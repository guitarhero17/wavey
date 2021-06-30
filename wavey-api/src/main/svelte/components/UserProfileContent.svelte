<script>
  import { url } from '@roxi/routify'
  import { getContext } from 'svelte';
  import { fade } from 'svelte/transition'
  import { retrieveAuthUserId } from '../lib/userAuthentication'
  import InfoInstrument from './InfoInstrument.svelte'
  import DeleteModal from '../components/DeleteModal.svelte'
  import Waves from './Waves.svelte'
  import convertInstrumentToPlayerType from '../lib/instrumentToPlayer'
  import tippy from 'sveltejs-tippy'
  export let user

  const { open } = getContext('simple-modal')

  const extractInitials = name => {
    const nameArray = name.split(' ')
    return nameArray.reduce((acc, el) => acc.charAt(0) + el.charAt(0))
  }

  const firstName = user.name.split(' ')[0]

  const tippyProps = {
    content: `Call ${user.name}`,
    allowHTML: true,
    placement: 'bottom',
    arrow: false,
    delay: 300,
    offset: [0,10]
  }

  function handleClickOnDelete() {
    open(DeleteModal)
  }


</script>

<style>

  .waves-bg-animated--chathead::before {
    opacity: 0;
    transition: opacity 300ms ease-in;
    width: 150%;
    height: 150%;
    top: -11px;
    left: -18%;
    transform: rotate(-30deg);
    animation-timing-function: linear;
  }

  .waves-bg-animated--chathead:hover::before {
    opacity: 1;
  }

  .chathead {
    transition: transform 300ms ease-in;
  }

  .chathead:hover{
    transform: scale(1.1);
    @apply border-waveyBrown;
  }
</style>


<article transition:fade>
  <!-- {#if user.id == retrieveAuthUserId()}
    <section class="edit-profile w-full bg-gray-900 flex justify-center items-center py-10">
      <a class="text-4xl text-waveyGreen" href={$url('/profile/edit')}>Edit your profile</a>
    </section>
  {/if} -->
  <section class="intro-section bg-black w-full text-white px-5 pt-20 pb-10">
    <div class="intro-wrapper relative">
      <div class="flex items-center">
        <div class="intro-photo w-1/2 h-auto">
          <img src={ $url(`/images/user-profile-pictures/user-${user.id}-profile-picture.jpg`) }
          class="w-full h-full rounded-2xl border border-waveyGreen"
          onerror="this.style.display='none'"
          alt="Profile">
        </div>
        {#if user.id != retrieveAuthUserId() && user.telephoneNumber}
          <div class="s:hidden chat-wrapper w-1/2 flex justify-center">
            <a use:tippy = { tippyProps } href={`tel: ${user.telephoneNumber}`} class="chathead waves-bg-animated waves-bg-animated--chathead rounded-full w-40 h-40 flex justify-center items-center border-4 border-waveyGreen font-bold text-7xl cursor-pointer">
              { extractInitials(user.name) }
            </a>
          </div>
        {/if}
      </div>
      {#if user.id != retrieveAuthUserId()}
        <div class="intro-name mt-8">
          <div class="text-2xl">This is</div>
          <div class="mt-2 text-6xl">{user.name}</div>
        </div>
      {/if}
    </div>
    <div class="profile-info mt-20 flex">
      <div class="info-instruments-container w-1/2 border-r border-waveyYellow">
        <div class="info-instruments-wrapper px-5">
          <div class="info-instrumentPrimary">
            {#if user.instrumentPrimary && user.instrumentPrimary.length > 0}
              <div class="text-3xl mb-4">{ user.id == retrieveAuthUserId() ? 'You primarily play ' : 'Primarily plays ' }</div>
              <InfoInstrument instrument = { user.instrumentPrimary } isPrimary = { true }/>
            {/if}
          </div>
          {#if user.instrumentsSecondary && user.instrumentsSecondary.length > 0}
            <div class="info-instrumentsSecondary mt-7">
              <div class="text-2xl mb-3">But { user.id == retrieveAuthUserId() ? 'you' : '' } can also jam on</div>
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
            <div class="text-3xl mb-4">{ user.id == retrieveAuthUserId() ? 'You are currently ' : 'Currently is ' }on the lookout for</div>
            {#each user.lookingFor as instrument}
              <div class="info-lookingFor-playerType text-xl mb-2">{ convertInstrumentToPlayerType(instrument) }</div>
            {/each}
          {/if}
        </div>
      </div>
    </div>
  </section>
  <section class="waves-section bg-gray-900 w-full text-white px-5 py-10 border-t border-waveyYellow">
    <Waves userId = { user.id } firstName = { firstName }/>
  </section>
  {#if user.id == retrieveAuthUserId()}
    <section class="delete-section w-full bg-black flex justify-center items-center py-10">
      <button on:click={ handleClickOnDelete } class="text-4xl text-red-500">Delete your profile</button>
    </section>
  {/if}
</article>
