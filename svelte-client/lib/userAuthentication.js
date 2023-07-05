import { get } from 'svelte/store'
// import { isAuthenticated, storedJwt, authUserID } from '../store'

const registerURL = 'https://localhost:8443/users'
const authURL = 'https://localhost:8443/authenticate'
const refreshTokenURL = 'https://localhost:8443/refreshtoken'

const registerUser = async (reqBody) => {
  return await fetch(registerURL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(reqBody)
  })
  .then(res => res.json())
  .then(data => {
    if(data.id) {
      return data
    } else {
      throw new Error(data.error)
    }
  })
}

const authenticateUser = async (reqBody) => {
  return await fetch(authURL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(reqBody)
  })
    .then(res => res.json())
    .then(data => {
      if (data.jwt && data.id) {
        saveJwt(data.jwt)
        saveAuthUserId(data.id)
        return data
      } else if (data.error && data.error == 'Bad credentials') {
        return data
      } else {
        throw new Error(data.error)
      }
    })
}

const fetchWithJwt = async (url, reqMethod = 'GET', reqBody = null) => {
  let log = `${reqMethod}-Request: ${url.split('https://localhost:8443')[1]}`
  if (!retrieveJwt()) {
    return null
  }
  const response = await makeRequestWithToken(url, reqMethod, reqBody)

  
  if (reqMethod === 'DELETE' && response.status === 204) {
    // console.log('Deleting..')
    return 'DELETED'
  }
  
  const responseJson = await response.json()

  if (responseJson.error) {
    // console.log(log + '\nError while fetching...')
    throw new Error(responseJson.error)
  }
  
  const errorCause = responseJson.cause

  if (!response.ok && !errorCause.startsWith('io.jsonwebtoken.ExpiredJwtException')) {
    // console.log(log + '\nThe following error occured:')
    throw new Error(responseJson)
  }

  if (errorCause && errorCause.startsWith('io.jsonwebtoken.ExpiredJwtException')) {
      // console.log(log + '\nThe jwt token has expired. Getting a new one...')
      let freshJwt = await refreshJwt()
      // console.log(log + '\nThe fresh jwt is: ' + freshJwt)

      if (freshJwt) {
        const consequentResponse = await makeRequestWithToken(url)

        if (consequentResponse.ok) {
          return consequentResponse.json()
        } else {
          throw new Error('The consequent request failed.')
        }
      } else {
        throw new Error('A refreshed Jwt could not be fetched (2)')
      }
  } else {
    // console.log(log + '\nJwt is valid')
    return responseJson
  }
}

const refreshJwt = async () => {
  // console.log('Refreshing jwt..')

  const response = await makeRequestWithToken(refreshTokenURL)

  if (response.ok) {
    let responseJson = await response.json(),
        freshJwt = responseJson.jwt,
        principalId = responseJson.id
    
    if (freshJwt) {
      storedJwt.set(freshJwt)
      localStorage.setItem('token', freshJwt)
      // console.log('Jwt refreshed!')
      return freshJwt
    } else if (responseJson.valid) {
      return retrieveJwt()
    }

  } else {
    // console.log('A refreshed jwt could not be fetched')
  }
}

// const saveJwt = jwt => {
//   try {
//     localStorage.setItem('token', jwt)
//     isAuthenticated.set(true)
//     storedJwt.set(jwt)
//     // console.log('Jwt stored!')
//   } catch (e) {
//     // console.log('The following error occured while setting the token in the local storage: ' + e )
//   }
// }

// const retrieveJwt = () => {
//   let jwt = null
//
//   if (get(storedJwt) && get(storedJwt) !== 'undefined') {
//     jwt = get(storedJwt)
//   } else if (localStorage.getItem('token') && localStorage.getItem('token') !== 'undefined') {
//     storedJwt.set(localStorage.getItem('token'))
//     jwt = localStorage.getItem('token')
//   } else {
//     // console.log('A jwt could not be found.')
//   }
//   return jwt
// }

// const saveAuthUserId = id => {
//   try {
//     localStorage.setItem('authID', id)
//     authUserID.set(id)
//     // console.log('User id saved')
//   } catch (e) {
//     // console.log('The following error occured while setting the user id in the local storage: ' + e )
//   }
// }

const retrieveAuthUserId = () => {
  let userId = null

  if (get(authUserID) && get(authUserID) !== 'undefined') {
    userId = get(authUserID)
  } else if (localStorage.getItem('authID') && localStorage.getItem('authID') !== 'undefined') {
    authUserID.set(localStorage.getItem('authID'))
    userId = localStorage.getItem('authID')
  } else {
    // console.log('A auth user id could not be found.')
  }
  return userId
}

// const makeRequestWithToken = async (url, reqMethod = 'GET', reqBody = null) => {
//   let jwt = retrieveJwt()
//
//   if (!jwt) {
//     // console.log('No user was logged in.')
//     return null
//   }
//   const reqHeaders = new Headers()
//   reqHeaders.append('Authorization', `Bearer ${retrieveJwt()}`)
//   reqHeaders.append('Content-Type', 'application/json')
//
//   const isUploadingFile = url.indexOf('/picture') > -1 && reqMethod === 'POST'
//
//   // if(!isUploadingFile) {
//   //   // console.log('us not uploading')
//   // } else {
//   // }
//
//   if (url.indexOf('refreshtoken') > -1) {
//     reqHeaders.append('refreshToken', 'true')
//   }
//
//   let fetchObject = {
//     method: reqMethod,
//     headers: reqHeaders
//   }
//
//   if (reqBody) {
//     fetchObject.body = isUploadingFile ? reqBody : JSON.stringify(reqBody)
//   }
//   return fetch(url, fetchObject)
// }

const logCurrentUserOut = () => {
  // console.log('Logging out...')
  localStorage.removeItem('token')
  localStorage.removeItem('authID')
  window.location.href = '/'
}

export { registerUser, authenticateUser, fetchWithJwt , retrieveAuthUserId , logCurrentUserOut}