// let getLocation = async () => {
  //   if (navigator.geolocation) {
  //     async function getCity (position) {
  //       let latitude = position.coords.latitude,
  //           longitude = position.coords.longitude

  //       // 2,500 requests per day for testing purposes
  //       let apiKey = 'c492c636b3af40ceae9f3247d9e2f080',
  //           apiURL = 'https://api.opencagedata.com/geocode/v1/json'

  //       let requestURL =
  //         apiURL +
  //         '?key=' +
  //         apiKey +
  //         '&q=' +
  //         encodeURIComponent(latitude + ',' + longitude) +
  //         '&pretty=1' +
  //         '&no_annotations=1'

  //       await fetch(requestURL)
  //         .then((res) => res.json())
  //         .then((data) => {
  //           cityGlobal = data.results[0].components.city
  //         })
  //     }

  //     navigator.geolocation.getCurrentPosition(getCity, () => console.log('Error while getting city...'))
  //   } else {
  //     console.log('navigator.geolocation could not be reached')
  //   }
  // }