<script>
  import { fade } from 'svelte/transition'
  import UserPreviewBoxCl from '../components/content-loaders/UserPreviewBoxCL.svelte'
  import UserPreviewBox from '../components/UserPreviewBox.svelte'
  import LoginModal from '../components/LoginModal.svelte'
  // import { isAuthenticated } from '../store'
  import { retrieveAuthUserId } from '../lib/userAuthentication'
  import { beforeUrlChange } from '@roxi/routify'

  $beforeUrlChange((event, store) => {
    const targetHref = event.url
    const linksForAuthUsers = ['/profile']
    const checkTarget = linksForAuthUsers.reduce((acc, currentVal) => acc || targetHref.startsWith(currentVal))

    // if (checkTarget && !$isAuthenticated) {
    //   open(LoginModal)
    //   return false
    // } else {
    //   return true
    // }
  })

  let usersDataJSON = loadUsers()
  usersDataJSON.then(h => console.log(h))
  let cityGlobal = 'your place'

  async function loadUsers() {
    const res = await fetch('https://localhost:8443/users')
    const data = await res.json()

    console.log('In index')

    if (res.ok) {
      return data
    } else {
      throw new Error(data)
    }
  }

  let loadFilteredUsers = (users) => {
    // if ($isAuthenticated) {
    //   return users.filter((user) => user.id != retrieveAuthUserId())
    // } else {
    //   return users
    // }
    return users
  }
</script>

<div class="main-content bg-black text-white">
  <h1 class="text-7xl l:text-6xl m:text-5xl font-bold tracking-tight flex justify-center items-center w-full mb-8 px-5 pt-8">
    Discover musicians {cityGlobal ? `in ${cityGlobal}` : `nearby`}
  </h1>
  <article class="px-5">
    <ul class="w-full flex flex-wrap">
      {#await usersDataJSON}
        {#each Array(10) as _,i}
          <UserPreviewBoxCl/>
        {/each}
      {:then data}
        {#each loadFilteredUsers(data._embedded.users) as user}
          <UserPreviewBox {user} />
        {/each}
      {:catch error}
        <div transition:fade class="w-full text-center">
          <h2 class="text-2xl">Oh no!</h2>
          <p class="text-xl">There was an error preventing us from loading our content.</p>
          <p class="mt-2 text-4xl font-bold">We'll back soon!</p>
        </div>
        {#each Array(10) as _,i}
          <UserPreviewBoxCl animateCL = { false }/>
        {/each}
      {/await}
    </ul>
  </article>
</div>