<script>
import { retrieveAuthUserId, logCurrentUserOut } from '../../utils/userAuthentication';
export let waveTitle = ''
export let waveId = ''

let wrapperDiv
const customCloseEvent = new CustomEvent('close-dialog', { bubbles: true})
  
async function handleYes () {
  // if (retrieveAuthUserId()) {
  const response = await fetch(`/api/waves/${waveId}`, {
    method: 'DELETE'
  })
  // console.log('Resp is ' + response)

  if (response.ok) {
    wrapperDiv.dispatchEvent(customCloseEvent)
    // setTimeout(() => {
    //   logCurrentUserOut()
    // }, 300)
  } else {
    console.log('Could not delete wave ', waveId)
  }
}

function handleNo() {
  wrapperDiv.dispatchEvent(customCloseEvent)
}
</script>

<div bind:this={wrapperDiv} class="flex justify-center items-center flex-col p-10">
  <div class="text-4xl text-center">
    Are you sure you want to delete the wave
    <span class="text-waveyGreen">{waveTitle}</span>
    ?
  </div>
  <div class="mt-7 w-1/2 flex justify-between">
    <button on:click={handleYes} class="text-2xl hover:scale-110">
      Yes
    </button>
    <button on:click={handleNo} class="text-2xl hover:scale-110">
      No
    </button>
  </div>
  <!--{#if userDeleted}-->
  <!--  <div class="mt-10 s:mt-5 text-3xl text-waveyBrown text-center">-->
  <!--    Your profile was deleted. Logging you out...-->
  <!--  </div>-->
  <!--{/if}-->
</div>