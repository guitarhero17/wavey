import { get } from 'svelte/store'
import { authenticatedUsername, encodedCredentials } from '../store'

export const USERS_URL = '/api/users'
const validateCredentialsURL = '/api/login'

const persistCredentials = (username, credentials) => {
  encodedCredentials.set(credentials)
  authenticatedUsername.set(username)

  try {
    localStorage.setItem('user', credentials)
  } catch (e) {
    console.log('Unable to use local storage')
  }
}

export const registerUser = async formData =>
  await fetch(USERS_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formData),
  }).then(res => {
    persistCredentials(formData.username, encodedCredentials)
    return res.json()
  })

export const validateCredentials = async formData => {
  const encodedCredentials = btoa(`${formData.username}:${formData.password}`)

  const res = await fetch(validateCredentialsURL, {
    method: 'GET',
    headers: {
      Authorization: `Basic ${encodedCredentials}`,
    },
  })

  if (res.ok) {
    persistCredentials(formData.username, encodedCredentials)
  } else {
    const json = await res.json()
    throw new Error(json.error)
  }

  return res.ok
}

export const fetchAuthorized = (url, options) =>
  fetch(url, {
    ...options,
    headers: {
      ...options.headers,
      Authorization: `Basic ${get(encodedCredentials)}`,
    },
  })

export const retrieveUserCredentials = () => {
  try {
    const credentials = localStorage.getItem('user')
    if (credentials) {
      encodedCredentials.set(credentials)
    }
    return credentials
  } catch (e) {
    console.log('Unable to use local storage')
  }
}

export const isAuthenticated = get(encodedCredentials).length > 0

export const logCurrentUserOut = () => {
  authenticatedUsername.set('')
  encodedCredentials.set('')

  try {
    localStorage.removeItem('user')
  } catch (e) {
    console.log('Unable to use local storage')
  }
}

export const retrieveAuthUserId = () => {}
