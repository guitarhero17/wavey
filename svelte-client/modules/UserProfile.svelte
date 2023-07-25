<script>
  import Modal from 'svelte-simple-modal'
  import { fade } from 'svelte/transition'
  import UserProfileContent from '../components/UserProfileContent.svelte'
  import { fetchAuthorized } from '../utils/userAuthentication'
  import LoadingWave from '../components/icons/LoadingWave.svelte'

  export let username
  
// const loadUser = async () => fetchAuthorized(`/api/users/${username}`)
const loadUser = async () => fetch(`/api/users/${username}`).then(res => res.json())

</script>

{#await loadUser()}
  <div transition:fade class="w-full flex justify-center items-center flex-col p-16">
    <p class="text-4xl pb-4">Loading {username}'s profile</p>
    <LoadingWave />
  </div>
{:then user}
  <Modal>
    <UserProfileContent { user }/>
  </Modal>
{:catch error}
  <div class="w-full text-center">
    <h2 class="text-2xl">Oh no!</h2>
    <p class="text-xl">There was an error preventing us from loading our content.</p>
    <p class="mt-2 text-4xl font-bold">We'll back soon!</p>
    <p>{ error }</p>
  </div>
{/await}