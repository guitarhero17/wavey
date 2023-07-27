<script>
  import { slide } from 'svelte/transition'
  import { url } from '@roxi/routify'
  import { getContext } from 'svelte'
  import { isAuthenticated, logCurrentUserOut } from '../utils/userAuthentication'
  import LoginModalContent from './modal/LoginModalContent.svelte'
  import HamburgerMenuToggle from './icons/HamburgerMenuToggle.svelte'

  let mobileHeaderVisible = false
  const { open } = getContext('simple-modal')

  function closeMobileHeader() {
    mobileHeaderVisible = false
  }

  function performLogging() { 
    if (isAuthenticated) {
      logCurrentUserOut()
    } else {
      open(LoginModalContent)
    }
    closeMobileHeader()
  }
</script>

<header
  class="waves-bg-animated waves-bg-animated--header px-16 m:px-6 flex flex-wrap items-center py-0 m:py-2"
>
  <div class="flex-1 flex justify-between items-center">
    <a href={$url('/')} on:click={closeMobileHeader}>
      <img
        class="block w-8 h-8"
        src={$url('/images/icons/Logo_2.svg')}
        alt="Wavey Logo"
      />
    </a>
  </div>
  <label for="menu-toggle" class="pointer-cursor hidden m:block">
    <HamburgerMenuToggle isOpen={mobileHeaderVisible}/>
  </label>
  <input
    class="hidden"
    type="checkbox"
    id="menu-toggle"
    bind:checked={mobileHeaderVisible}
  />
  <div class="flex items-center w-auto m:hidden">
    <button class="p-4 m:py-3 m:px-0 hover:text-waveyYellow m:text-center m:text-xl m:font-bold"
      on:click={performLogging}>
      {isAuthenticated ? 'Log Out' : 'Log In'}
    </button>
    {#if isAuthenticated}
      <button
        class="ml-4 m:ml-0 flex items-center justify-start mb-0 m:mb-4 pointer-cursor"
      >
        <a href={$url('/profile')} class="m:hidden">
          <img
            class="rounded-full w-10 h-10 border-2 border-transparent hover:bg-waveyYellow bg-white"
            src={$url('../images/icons/profile_placeholder.svg')}
            alt="profile placeholder"
          />
        </a>
        <a
          href={$url('/profile')}
          class="hidden m:block m:w-full m:text-xl hover:text-waveyYellow font-bold text-center">
          Profile
        </a>
      </button>
    {/if}
  </div>

  {#if mobileHeaderVisible}
    <div
      transition:slide
      class="flex items-center m:w-full m:flex-col"
      id="menu-mobile"
    >
      <button
        class:hidden={isAuthenticated}
        class="p-4 m:py-3 m:px-0 hover:text-waveyYellow m:text-center m:text-xl m:font-bold"
        on:click={performLogging}
      >
        {isAuthenticated ? 'Log out' : 'Log In'}
      </button>
      <a
        href={$url('/profile')}
        on:click={ closeMobileHeader }
        class="ml-4 m:ml-0 flex items-center justify-start mb-0 m:mb-4 pointer-cursor"
      >
        <img
          class="m:hidden rounded-full w-10 h-10 border-2 border-transparent hover:border-indigo-400 bg-white"
          src={$url('/images/icons/profile_placeholder.svg')}
          alt="profile placeholder"
        />
        <p
          class="hidden m:block m:w-full m:text-xl hover:text-waveyYellow font-bold text-center"
        >
          Profile
        </p>
      </a>
    </div>
  {/if}
</header>

<style>
  header {
    position: relative;
  }

  .waves-bg-animated--header::before {
    transition: opacity 300ms ease-in;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    transform: none;
    animation-timing-function: ease-in-out;
  }
</style>
