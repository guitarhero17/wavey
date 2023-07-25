<script>
  import { url } from '@roxi/routify'
  import { getContext } from 'svelte';
  import { fade } from 'svelte/transition'
  import { retrieveAuthUserId } from '../utils/userAuthentication'
  import InfoInstrument from './InfoInstrument.svelte'
  import DeleteModal from './modal/DeleteModal.svelte'
  import Waves from './Waves.svelte'
  import getPlayerType from '../utils/instrumentToPlayer'

  export let user

  const { open } = getContext('simple-modal')

  const username = user.username

  const firstName = user.name.split(' ')[0]

  const tippyProps = {
    content: `Call ${user.name}`,
    allowHTML: true,
    placement: 'bottom',
    arrow: false,
    delay: 300,
    offset: [0,10]
  }
</script>

<article>
  <!-- {#if username == retrieveAuthUserId()}
    <section class="edit-profile w-full bg-gray-900 flex justify-center items-center py-10">
      <a class="text-4xl text-waveyGreen" href={$url('/profile/edit')}>Edit your profile</a>
    </section>
  {/if} -->
  <section class="bg-black w-full px-5 pt-20 pb-10">
    <div class="relative">
      <div class="flex items-center">
        <div class="w-1/2 h-auto">
          <img src={ $url(`../images/user-profile-pictures/${username}/main.jpg`) }
            class="w-full h-full rounded-2xl border border-waveyGreen"
            onerror="this.style.display='none'"
            alt="Profile">
        </div>
      </div>
      <!-- {#if username != retrieveAuthUserId()} -->
        <div class="intro-name mt-8">
          <p class="text-2xl">This is</p>
          <p class="mt-2 text-6xl">{user.name}</p>
        </div>
      <!-- {/if} -->
    </div>
    <div class="profile-info mt-20 flex">
      <div class="w-1/2 border-r border-waveyYellow">
        <div class="pr-5">
          <div class="info-instrumentPrimary">
            {#if user.instrumentPrimary && user.instrumentPrimary.length > 0}
              <div class="text-3xl mb-4">{ username == retrieveAuthUserId() ? 'You primarily play ' : 'Primarily plays ' }</div>
              <InfoInstrument instrument = { user.instrumentPrimary } isPrimary/>
            {/if}
          </div>
          {#if user.instrumentsSecondary && user.instrumentsSecondary.length > 0}
            <div class="info-instrumentsSecondary mt-7">
              <div class="text-2xl mb-3">But { username == retrieveAuthUserId() ? 'you' : '' } can also jam on</div>
              <div class="info-instrumentsSecondary-wrapper flex flex-wrap">
                {#each user.instrumentsSecondary as instrument}
                  <InfoInstrument { instrument }/>
                {/each}
              </div>
            </div>
          {/if}
        </div>
      </div>
      <div class="info-lookingFor-container w-1/2">
        <div class="info-lookingFor-wrapper px-5">
          {#if user.lookingFor && user.lookingFor.length > 0}
            <div class="text-3xl mb-4">{ username == retrieveAuthUserId() ? 'You are currently ' : 'Currently ' }on the lookout for</div>
            {#each user.lookingFor as instrument}
              <div class="info-lookingFor-playerType text-xl mb-2">{ getPlayerType(instrument) }</div>
            {/each}
          {/if}
        </div>
      </div>
    </div>
  </section>
  <section class="bg-gray-900 w-full px-5 py-10 border-t border-waveyYellow">
    <Waves username = { username } firstName = { firstName }/>
  </section>
  <section class="bg-black w-full px-5 py-10"> 
    <p class="text-3xl">You like their style? Why don't you <a href={`mailto:${user.email || ''}`} class="text-waveyGreen hover:underline">contact {firstName}</a> and jam together!</p>
  </section>
  <!-- {#if username == retrieveAuthUserId()} -->
    <section class="w-full bg-black flex justify-center items-center py-10">
      <button on:click={ () => open(DeleteModal) } class="text-4xl text-red-500">Delete your profile</button>
    </section>
  <!-- {/if} -->
</article>

