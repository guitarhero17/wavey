<script>
  import Modal from 'svelte-simple-modal'
  import { fade } from 'svelte/transition'
  import UserProfileContent from '../components/UserProfileContent.svelte'
  import UserProfileCL from '../components/content-loaders/UserProfileCL.svelte'
  import { fetchAuthorized } from '../lib/userAuthentication'

  export let username
  
// const loadUser = async () => fetchAuthorized(`/api/users/${username}`)
const loadUser = async () => fetch(`/api/users/${username}`).then(res => res.json())

</script>

{#await loadUser()}
  <UserProfileCL/>
{:then user}
  <Modal>
    <UserProfileContent { user }/>
  </Modal>
{:catch error}
<!-- transition:fade -->
  <div class="w-full text-center">
    <h2 class="text-2xl">Oh no!</h2>
    <p class="text-xl">There was an error preventing us from loading our content.</p>
    <p class="mt-2 text-4xl font-bold">We'll back soon!</p>
    <p>{ error }</p>
  </div>
{/await}