<script>
  import { getContext } from 'svelte'
  import { goto } from '@roxi/routify'
  import { fade, fly } from 'svelte/transition'
  import { sineOut } from 'svelte/easing'
  import { Wave } from 'svelte-loading-spinners'
  import { registerUser, validateCredentials } from '../../lib/userAuthentication'

  const { close } = getContext('simple-modal')
  let isOpen = false
  let isFormSubmitting = false
  let responseValid = false
  let badCredentials = false
  let responseRegisterValid = false

  const handleSubmitLogin = async (e) => {
    isFormSubmitting = true
    const formDataObject = Object.fromEntries(new FormData(e.target))
    const areCredentialsValid = await validateCredentials(formDataObject)
    isFormSubmitting = false
    responseValid = areCredentialsValid

    setTimeout(() => {
      close()
      $goto('/index')
    }, 600)
  }

  const handleSubmitRegister = async (e) => {
    isFormSubmitting = true
    const formDataObject = Object.fromEntries(new FormData(e.target))
    let responseData = await registerUser(formDataObject)
    isFormSubmitting = false

    if (responseData.error) {
      responseRegisterValid = false
    } else {
      setTimeout(() => {
        isOpen = false
      }, 300)
      responseRegisterValid = true
    }
  }
</script>

<div class="login-side">
  <h2 class="text-center s:text-3xl text-4xl">Log in to your Wavey account</h2>
  <div transition:fade class:hidden = { !badCredentials || responseValid } class="text-red-500 text-center"> Username or password wrong. Try again.</div>

  <form
    on:submit|preventDefault= {handleSubmitLogin}
    class="mt-7 text-center grid place-items-center"
  >

    <input class="text-center rounded-md text-black h-10 s:h-6 block" name="username" required min="4" placeholder="Wavey Username"/>
    <input class="text-center rounded-md text-black h-10 s:h-6 block mt-3" name="password" required min="6" type="password" placeholder="Password" />
    <button type="submit" class:hidden={ isFormSubmitting || responseValid } class="mt-4 text-xl text-waveyGreen hover:underline hover:border-waveyGreen">Log In</button>

    <div class:hidden={ isFormSubmitting || responseValid } class="text-center grid place-items-center mt-10">
      <p class="text-xl">Don't have an account yet?</p>
      <button type="button" class="text-waveyYellow mt-2" on:click="{ () => isOpen = true }">Sign up here</button>
    </div>

    {#if isFormSubmitting}
      <div class="mt-6 flex justify-center">
        <Wave size="60" color="#37C9B7" unit="px" duration="1s"/>
      </div>
    {/if}
  </form>
  <div transition:fade class:hidden = { !responseValid } class="mt-10 s:mt-5 text-center text-3xl text-waveyGreen">You are now logged in!</div>
</div>
{#if isOpen}
  <div transition:fly={{ y: '100%', easing: sineOut, duration: 600 }} class="rounded-3xl absolute top-0 left-0 w-full h-full grid place-content-center bg-gray-900 z-10 px-5">
    <img 
      src={'/images/svg/back-arrow.svg'}
      on:click="{ () => isOpen = false }"
      on:keydown="{ () => isOpen = false }"
      class="absolute top-5 left-5 w-10 h-10 -rotate-90 cursor-pointer" alt="Back arrow"/>
    <h2 class="text-center s:text-3xl text-4xl">Join your local musicians.</h2>
    <h2 class="text-center s:text-3xl text-4xl">Share your waves.</h2>
    <form
      on:submit|preventDefault={handleSubmitRegister}
      class="text-center grid place-items-center mt-12"
    >
    <input class="text-center rounded-md text-black h-10 s:h-6 block" name="username" required min="4" label="Set your Wavey Id" placeholder="Wavey Username"/>
    <input class="text-center rounded-md text-black h-10 s:h-6 block mt-3" name="name" min="2" required label="How would you like us to call you?" placeholder="Your Name"/>
    <input class="text-center rounded-md text-black h-10 s:h-6 block mt-3" name="password" min="6" required label="Type in your password" type="password" placeholder="Password"/>
    <button type="submit" disabled={isFormSubmitting} class="mt-10 text-xl text-waveyGreen hover:underline hover:border-waveyGreen">
      Sign Up
    </button>
    {#if isFormSubmitting}
      <div class="mt-6 flex justify-center">
        <Wave size="60" color="#37C9B7" unit="px" duration="1s"/>
      </div>
    {/if}
    {#if responseRegisterValid}
      <div transition:fade class="mt-10 s:mt-5 text-center text-3xl text-waveyGreen">Registered!</div>
      <div transition:fade class="mt-5 s:mt-3 text-center text-2xl text-waveyYellow">Please log in now</div>
    {/if}
  </form> 
</div>
{/if}

<style>
  button:disabled {
    cursor: default;
    opacity: 0.5;

    &:hover {
      border: none;
    }
  }
</style>