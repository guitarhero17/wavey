<script>
  import { onMount } from 'svelte'
  import { slide } from 'svelte/transition'
  import { url } from '@roxi/routify'
  import { getContext } from 'svelte'

  import lottie from 'lottie-web'
  import { isAuthenticated, logCurrentUserOut } from '../lib/userAuthentication'
  import LoginModalContent from './modal/LoginModalContent.svelte'

  let lottieDirection = 1
  let lottieAnimation
  let mobileHeaderVisible = false
  const { open } = getContext('simple-modal')

  onMount(() => {
    lottieAnimation = lottie.loadAnimation({
      container: document.getElementById('hamburger-menu'),
      renderer: 'svg',
      loop: false,
      autoplay: false,
      path: $url('/images/header/lottie/menuV4_white.json'),
    })
    lottieAnimation.setSpeed(2.5)
  })

  function closeMobileHeader() {
    mobileHeaderVisible = false
    transformHamburgerIcon()
  }

  function transformHamburgerIcon() {
    lottieAnimation.setDirection(lottieDirection)
    lottieAnimation.play()
    lottieDirection = -lottieDirection
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
        src={$url('/images/svg/Logo_2.svg')}
        alt="Wavey Logo"
      />
    </a>
  </div>
  <label for="menu-toggle" class="pointer-cursor hidden m:block">
    <div id="hamburger-menu" on:click={transformHamburgerIcon} />
  </label>
  <input
    class="hidden"
    type="checkbox"
    id="menu-toggle"
    bind:checked={mobileHeaderVisible}
  />
  <div class="flex items-center w-auto m:hidden" id="menu">
    <button class="p-4 m:py-3 m:px-0 hover:text-waveyYellow m:text-center m:text-xl m:font-bold"
      on:click={performLogging}>
      {isAuthenticated ? 'Log Out' : 'Log In'}
    </button>
    <!-- <li>
        <div class="relative flex w-full m:w-1/2 s:w-full m:mx-auto px-4 flex-wrap items-stretch">
          <div class="flex">
            <span class="font-normal leading-snug flex text-center white-space-no-wrap border border-solid border-orange-600 rounded-full text-sm bg-orange-100 items-center rounded-r-none pl-2 border-r-0 placeholder-waveyGreen">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search">
                <circle cx="11" cy="11" r="8"></circle>
                <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
              </svg>
            </span>
          </div>
          <input type="text" class="px-2 py-1 h-8 border border-solid  border-orange-600 rounded-full text-sm leading-snug text-orange-700 bg-orange-100 shadow-none outline-none focus:outline-none w-full font-normal rounded-l-none flex-1 border-l-0 placeholder-orange-300" placeholder="Search orange" />
        </div>
      </li> -->
    {#if isAuthenticated}
      <button
        class="ml-4 m:ml-0 flex items-center justify-start mb-0 m:mb-4 pointer-cursor"
      >
        <a href={$url('/profile')} class="m:hidden">
          <img
            class="rounded-full w-10 h-10 border-2 border-transparent hover:bg-waveyYellow bg-white"
            src={$url('../images/svg/profile_placeholder.svg')}
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
          src={$url('/images/svg/profile_placeholder.svg')}
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
