<script>
  import { onMount } from 'svelte'
  import { url } from '@roxi/routify'
  import { retrieveAuthUserId } from '../utils/userAuthentication'
  // import { fetchWithJwt } from '../lib/userAuthentication'
  import ReactionBlock from './ReactionBlock.svelte'
  import WaveSurfer from 'wavesurfer.js'

  export let username
  export let firstName

  let wavesurfers = []
  const responseJson = fetch(`/api/users/${username}/waves`).then(res => res.json())
  let currentlyPlaying = null  

  onMount(async () => {
    const data = await responseJson

    if(data._embedded) {
      wavesurfers = data._embedded.waves.map((wave) => {
        const soundwave = WaveSurfer.create({
          container: `#wave-${username}-${wave.id}`,
          barWidth: 3
        })

        soundwave.on('finish', () => {
          currentlyPlaying = null
        })

        soundwave.load($url(`../waves/${username}/${wave.fileName}`))

        return {
          waveId: wave.id,
          soundwave
        }
      })
    } else {
      console.log('This user probably does not have any waves yet')
    }
  })

  const togglePlay = (waveId) => {
    const wave = wavesurfers.find(entry => entry.waveId === waveId)?.soundwave
    wave?.playPause()

    if (wave?.isPlaying()) {
      wavesurfers.filter(entry => entry.waveId !== waveId).forEach(wave => wave.soundwave.stop())
      currentlyPlaying = waveId
    } else {
      currentlyPlaying = null
    }
  }
</script>

{#await responseJson}
  <p>Waiting for the tides...</p>
{:then data}
  {#if data._embedded}
    <div class="text-4xl mt-2 pl-5">
      Hear { firstName }'s waves
    </div>
    {#each data._embedded.waves as wave}
      <div class="mt-6 px-5">
        <div id={`wave-${username}-${wave.id}`} />
        <div class="flex justify-between items-center mt-6">
          <div class="flex items-center">
            <div
              class:playing={currentlyPlaying == wave.id}
              class="playButton mr-4"
              on:click={() => togglePlay(wave.id)}
            />
            <div class="text-xl">
              {wave.title}
            </div>
          </div>
          <ReactionBlock waveId={wave.id}/>
        </div>
      </div>
    {/each}
  {:else}
  <p class="text-4xl mt-2">{ username == retrieveAuthUserId() ? 'You have not ' : firstName + ' has not '}posted any waves yet...</p>
  {/if}
{:catch error}
  <p class="hidden">There was a network error...</p>
{/await}

<style>


</style>