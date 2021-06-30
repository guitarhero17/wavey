<script>
  import { Form, Input } from 'sveltejs-forms'
  import { goto } from '@roxi/routify'
  import { fade } from 'svelte/transition'
  import { Wave } from 'svelte-loading-spinners'
  import * as yup from 'yup'
  import { authenticateUser, registerUser } from '../lib/userAuthentication'

  let isOpen = false
  let responseValid = false
  let badCredentials = false
  let responseRegisterValid = false

  const schemaLogin = yup.object().shape({
    username: yup.string().min(4),
    password: yup.string().min(4),
  })

  const schemaRegister = yup.object().shape({
    username: yup.string().min(4).required(),
    name: yup.string().required().length(3),
    password: yup.string().min(4).required(),
  })

  const handleSubmit = async ({ detail: { values, setSubmitting, resetForm } }) => {
    let response = await authenticateUser(values)

    if (response) {
      setSubmitting(false)

      if (response.error) {
        badCredentials = true
      } else {
        responseValid = true
      }
      // console.log('Logged in!')

      setTimeout(() => {
        document.querySelector('button.close').click()
        $goto('/index')
      }, 400)
    }
  }

  const handleSubmitRegister = async ({ detail: { values, setSubmitting, resetForm } }) => {
    // setSubmitting()
    // responseRegisterValid = true
    let response = await registerUser(values)

    if (response) {
      setSubmitting(false)
      responseRegisterValid = true
      setTimeout(() => {
        document.getElementById('go-back').click()
      }, 300)
    }
  }
  const handleReset = () => {
    // console.log('Form is reset')
  }

</script>

<style>
  button:disabled {
    cursor: default;
    opacity: 0.5;
  }

  button:disabled:hover {
    border: none;
  }
</style>

<div class="login-side">
  <h2>Log in to your Wavey account</h2>
  <div transition:fade class:hidden = { !badCredentials || responseValid } class="text-red-500 text-center"> Username or password wrong. Try again.</div>

  <Form
    {schemaLogin}
    on:submit= {handleSubmit}
    on:reset = {handleReset}
    let:isSubmitting>

    <Input name="username" placeholder="Wavey Id"/>
    <Input name="password" type="password" placeholder="Password"/>
    <button type="submit" class:hidden={ isSubmitting || responseValid } class="mt-2 text-xl text-waveyGreen hover:border-b-2 hover:border-waveyGreen">Log In</button>
    <div class:hidden={ isSubmitting || responseValid } class="sign-up-prompt flex justify-center items-center mt-4 s:flex-col">
      <p class="mr-5 text-xl">Don't have an account yet?</p>
      <button type="button" class="text-waveyYellow" on:click="{ () => isOpen = true }">Sign up here</button>
    </div>
    {#if isSubmitting}
      <div class="mt-6 flex justify-center">
        <Wave size="60" color="#37C9B7" unit="px" duration="1s"/>
      </div>
    {/if}
  </Form>
  <div transition:fade class:hidden = { !responseValid } class="mt-10 s:mt-5 text-center text-3xl text-waveyGreen">You are now logged in!</div>
</div>
<div class:is-open="{ isOpen }" class="signup-side p-11">
  <div id="go-back" on:click="{ () => isOpen = false }"></div>
  <h2 class="text-center text-3xl">Join your local musicians. Share your waves</h2>
  <Form
    {schemaRegister}
    on:submit= {handleSubmitRegister}
    on:reset = {handleReset}
    let:isSubmittingRegister>

    <Input name="username" label="Set your Wavey Id" placeholder="Your Wavey Id"/>
    <Input name="name" label="How would you like us to call you?" placeholder="Your Name"/>
    <Input name="password" label="Type in your password" type="password" placeholder="Password"/>
    <button type="submit" disabled={isSubmittingRegister} class="mt-2 text-xl text-waveyGreen hover:border-b-2 hover:border-waveyGreen">Sign Up</button>
    {#if isSubmittingRegister}
      <div class="mt-6 flex justify-center">
        <Wave size="60" color="#37C9B7" unit="px" duration="1s"/>
      </div>
    {/if}
    <div transition:fade class:hidden = {!responseRegisterValid} class="mt-10 s:mt-5 text-center text-3xl text-waveyGreen"> Registered!</div>
    <div transition:fade class:hidden = {!responseRegisterValid} class="mt-5 s:mt-3 text-center text-2xl text-waveyYellow"> Please log in now</div>
  </Form>
</div>
