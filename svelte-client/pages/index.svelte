<script>
  import { onMount } from 'svelte'
  import UserPreviewBox from '../components/UserPreviewBox.svelte'
  // import LoginModalContent from '../components/modal/LoginModalContent.svelte'
  import { authenticatedUsername } from '../store'
  import { USERS_URL, isAuthenticated, retrieveAuthUserId, retrieveUserCredentials } from '../utils/userAuthentication'
  import { fade } from 'svelte/transition'
  import LoadingWave from '../components/icons/LoadingWave.svelte'
  // import { beforeUrlChange } from '@roxi/routify'

  // $beforeUrlChange((event, store) => {
  //   const targetHref = event.url
  //   const linksForAuthUsers = ['/profile']
  //   const checkTarget = linksForAuthUsers.reduce((acc, currentVal) => acc || targetHref.startsWith(currentVal))

  //   if (checkTarget && !$isAuthenticated) {
  //     open(LoginModal)
  //     return false
  //   } else {
  //     return true
  //   }
  // })

  onMount(() => {
    retrieveUserCredentials()
  })

const loadUsers = async () => fetch(USERS_URL).then(res => res.json())

  let loadFilteredUsers = (users) => {
    if (isAuthenticated) {
      console.log(`Authenticated user: ${$authenticatedUsername}`)
      return users.filter((user) => user.username != $authenticatedUsername)
    } else {
      return users
    }
  }
</script>

<div transition:fade>
  <h1 class="text-7xl l:text-6xl m:text-5xl font-bold tracking-tight flex justify-center items-center w-full mb-8 px-5 pt-8">
    Discover the best musicians in Berlin
  </h1>
  <article class="px-5">
    <ul class="w-full flex flex-wrap">
      {#await loadUsers()}
        <div transition:fade class="absolute w-full flex justify-center p-8">
          <LoadingWave />
        </div>
      {:then data}
        {#each loadFilteredUsers(data._embedded.users) as user}
          <UserPreviewBox {user} />
        {/each}
      {:catch error}
        <div class="w-full text-center">
          <h2 class="text-2xl">Oh no!</h2>
          <p class="text-xl">There was an error preventing us from loading our content.</p>
          <p class="mt-2 text-4xl font-bold">We'll back soon!</p>
        </div>
      {/await}
    </ul>
  </article>
</div>