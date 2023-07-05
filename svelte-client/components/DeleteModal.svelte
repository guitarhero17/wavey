<script>
import { fetchWithJwt, retrieveAuthUserId, logCurrentUserOut } from '../lib/userAuthentication';

  let userDeleted = false

  function handleNo () {
    document.querySelector('button.close').click()
  }
  
  async function handleYes () {
    if (retrieveAuthUserId()) {
      const userURL = `https://localhost:8443/users/${retrieveAuthUserId()}`
      const response = await fetchWithJwt(userURL, 'DELETE')
      // console.log('Resp is ' + response)

      if (response === 'DELETED') {
        userDeleted = true
        setTimeout(() => {
          logCurrentUserOut()
        }, 300)
      }
    } else {
      throw Error('User was not logged in.')
    }
  }
</script>

<div class="delete-modal flex justify-center items-center flex-col p-10">
  <div class="text-4xl text-red-600 text-center">Are you sure you want to delete your account?</div>
  <div class="delete-buttons mt-7 w-1/2 flex justify-between">
    <button on:click={handleNo} class="text-2xl text-waveyGreen">
      No
    </button>
    <button on:click={handleYes} class="text-2xl text-waveyGreen">
      Yes
    </button>
  </div>
  {#if userDeleted}
    <div class="mt-10 s:mt-5 delete-success text-3xl text-waveyBrown text-center">
      Your profile was deleted. Logging you out...
    </div>
  {/if}
</div>