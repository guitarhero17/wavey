<script>
  import { fade } from 'svelte/transition'
  import { url } from '@roxi/routify'
  import { retrieveAuthUserId } from '../lib/userAuthentication'

  export let user

</script>

<style>
  .upb {
    position: relative;
    bottom: 0;
    transition: bottom 200ms ease-in, box-shadow 200ms ease-in, border 200ms ease-in;
  }
  .upb:hover {
    bottom: 0.5rem;
  }

@media (min-width: 768px) {
  .waves-bg-animated--upb::before {
    opacity: 0;
    transition: opacity 300ms ease-in;
    width: 150%;
    height: 150%;
    top: -11px;
    left: -18%;
    transform: rotate(-30deg);
    animation-timing-function: linear;
  }

  .waves-bg-animated--upb:hover::before {
    opacity: 1;
  }
}

@media (max-width: 767px) {
  .waves-bg-animated--upb::before {
    opacity: 1;
    animation: none;
    height: 200%;
    top: -25%;
    left: -21%;

  }
}

@media (max-width: 600px) {
  .waves-bg-animated--upb::before {
    width: 150%;
    height: 150%;
    top: -11px;
    left: -18%;
  }
}
</style>

{#if user.id != retrieveAuthUserId()}
  <li class="w-1/4 mb-4 m:w-1/3 s:w-1/2 xs:w-full">
    <div transition:fade="{{delay: 300, duration: 400}}" class="upb waves-bg-animated waves-bg-animated--upb mx-4 mt-4 hover:shadow-xl rounded-xl pb-3 cursor-pointer border border-gray-900 group">
      <a href={$url('/profile/:id', {id: user.id})}>
        <div style="background-image: url('{$url(`/images/user-profile-pictures/user-${user.id}-profile-picture.jpg`)}')" class="h-40 bg-cover bg-no-repeat bg-black rounded-t-xl"></div>
        <div class="mt-3 flex justify-between items-center">
          {#if user.instrumentPrimary && user.instrumentPrimary.length > 0}
          <div class="ml-2 rounded-full w-10 h-10">
            <img class="w-full" src="{$url(`/images/instruments-logos/${user.instrumentPrimary}.svg`)}" alt="{user.instrumentPrimary}">
          </div>
          {/if}
          <div class="mr-2 name-wrapper group-hover:text-white">
            <p class="text-base">{user.name.toUpperCase()}</p>
            {#if user.instrumentPrimary && user.instrumentPrimary.length > 0}
            <p class="text-right text-waveyYellow font-bold">{user.instrumentPrimary.toLowerCase()}</p>
            {/if}
          </div>
        </div>
      </a>
    </div>
  </li>
{/if}
