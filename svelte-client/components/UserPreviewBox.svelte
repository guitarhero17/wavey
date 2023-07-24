<script>
  import { fade } from 'svelte/transition'
  import { url } from '@roxi/routify'

  export let user

  const primaryInstrument = user.instrumentPrimary.toLowerCase()

</script>

<!--{#if user.id != retrieveAuthUserId()}-->
  <li transition:fade class="w-1/4 m:w-1/3 s:w-1/2 xs:w-full mb-4">
    <!-- transition:fade="{{duration: 400}}" -->
    <div class="relative bottom-0 transitions hover:bottom-2 waves-bg-animated waves-bg-animated--upb mx-4 mt-4 hover:shadow-xl rounded-xl pb-3 cursor-pointer border border-gray-600 group">
      <a class="block h-full" href={$url('./profile/:username', {username: user.username})}>
        <img class="rounded-t-xl w-full h-44 m:h-40 s:h-36 xs:h-28 object-cover" src={$url('../images/user-profile-pictures/:username/main.jpg', { username: user.username})} alt={`Main profile picture of user ${user.username}`}>
        <div class="mt-3 flex justify-between items-center">
          {#if user.instrumentPrimary?.length > 0}
            <div class="ml-2 rounded-full w-10 h-10">
              <img class="w-full" src="{$url(`../images/instruments-logos/${primaryInstrument}.svg`)}" alt={`Image of the instrument ${primaryInstrument}`}>
            </div>
          {/if}
          <div class="mr-2 name-wrapper group-hover:text-white">
            <p class="text-base font-bold">{user.name.toUpperCase()}</p>
            {#if user.instrumentPrimary?.length > 0}
              <p class="text-right text-waveyYellow">{primaryInstrument}</p>
            {/if}
          </div>
        </div>
      </a>
    </div>
  </li>
<!--{/if}-->

<style>
  .transitions {
    transition: bottom 200ms ease-in, box-shadow 200ms ease-in, border 200ms ease-in;
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
