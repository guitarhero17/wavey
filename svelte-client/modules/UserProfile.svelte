<script>
  // import Modal from 'svelte-simple-modal'
  import { fade } from 'svelte/transition'
  import UserProfileContent from '../components/UserProfileContent.svelte'
  import UserProfileCL from '../components/content-loaders/UserProfileCL.svelte'

  export let username
  
  async function loadUser() {
    return await fetch(`https://localhost:8443/users/${username}`)
  }
</script>

{#await loadUser()}
  <UserProfileCL/>
{:then user}
  {#if user !== null}
<!--    <Modal>-->
<!--      <UserProfileContent { user }/>-->
<!--    </Modal>-->
  {:else}
    <p>User was null, open modal</p>
  {/if}
{:catch error}
  <div transition:fade class="w-full text-center">
    <h2 class="text-2xl">Oh no!</h2>
    <p class="text-xl">There was an error preventing us from loading our content.</p>
    <p class="mt-2 text-4xl font-bold">We'll back soon!</p>
    <p>{ error }</p>
  </div>
{/await}