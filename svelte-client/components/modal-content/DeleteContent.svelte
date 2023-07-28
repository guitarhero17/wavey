<script>
  import {createEventDispatcher, getContext} from 'svelte';
import { retrieveAuthUserId, logCurrentUserOut } from '../../utils/userAuthentication';

let userDeleted = false
let wrapperDiv
const customCloseEvent = new CustomEvent('close-dialog', { bubbles: true})
  
async function handleYes () {
  // if (retrieveAuthUserId()) {
  const userURL = `/api/users/${retrieveAuthUserId()}`
  // const response = await fetchWithJwt(userURL, 'DELETE')
  const response = await fetch(userURL, {
    method: 'DELETE'
  })
  // console.log('Resp is ' + response)

  if (response.ok) {
    userDeleted = true
    wrapperDiv.dispatchEvent(customCloseEvent)
    // dispatch('close')
    // setTimeout(() => {
    //   logCurrentUserOut()
    // }, 300)
  }
  // } else {
  //   throw Error('User was not logged in.')
  // }
}

function handleNo() {
  wrapperDiv.dispatchEvent(customCloseEvent)
}
</script>

<div bind:this={wrapperDiv} class="flex justify-center items-center flex-col p-10">
  <div class="text-4xl text-red-600 text-center">Are you sure you want to delete your account?</div>
  <div class="mt-7 w-1/2 flex justify-between">
    <button on:click={handleNo} class="text-2xl text-waveyGreen hover:scale-110">
      No
    </button>
    <button on:click={handleYes} class="text-2xl text-waveyGreen hover:scale-110">
      Yes
    </button>
  </div>
  {#if userDeleted}
    <div class="mt-10 s:mt-5 text-3xl text-waveyBrown text-center">
      Your profile was deleted. Logging you out...
    </div>
  {/if}
</div>