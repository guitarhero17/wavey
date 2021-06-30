<script>
  import { onMount } from 'svelte'
  import { url } from '@roxi/routify'
  import { retrieveAuthUserId } from '../lib/userAuthentication'
  import { fetchWithJwt } from '../lib/userAuthentication'
  import ReactionBlock from './ReactionBlock.svelte'
  import WaveSurfer from 'wavesurfer.js'

  export let userId
  export let firstName

  let wavesurfers = []
  let response = null
  let currentlyPlaying = false
  
  response = fetchWithJwt(
    `https://localhost:8443/users/${userId}/waves`
  )

  onMount(async () => {

    let data = await response,
        dataEmbedded = data._embedded

    if(dataEmbedded) {
      const wavesArray = dataEmbedded.waves

      wavesArray.forEach(wave => {
        let soundwave = WaveSurfer.create({
          container: `#wave-${wave.id}`,
          barWidth: 3
        })

        soundwave.on('finish', () => {
          currentlyPlaying = null
        })

        wavesurfers.push({
          waveId: wave.id,
          soundwave: soundwave
        })
      })
    
      wavesurfers.forEach(entry => {
        entry.soundwave.load($url(`/waves/user-${userId}-waves/user-${userId}-wave-${entry.waveId}.mp3`))
      })
    } else {
      // console.log('This user probably does not have any waves yet')
    }

  })

  const togglePlay = (waveId) => {
    let wave = wavesurfers.filter(entry => entry.waveId == waveId)[0]['soundwave']
    wave.playPause()

    if (wave.isPlaying()) {
      wavesurfers.filter(entry => entry.waveId !== waveId).forEach(wave => wave.soundwave.pause())
      currentlyPlaying = waveId
    } else {
      currentlyPlaying = null
    }
  }
</script>

<style>
  .button {
    border: 0;
    box-sizing: border-box;
    width: 0;
    height: 20px;
    /* waveyBrown */
    background-color: transparent;
    border-color: transparent transparent transparent #98473E;
    transition: 100ms all ease;
    cursor: pointer;
    border-style: solid;
    border-width: 10px 0 10px 15px;
  }

  .button.playing {
    border-style: double;
    border-width: 0px 0 0px 15px;
    border-color: transparent transparent transparent #F2F79E;
  }
</style>

{#await response}
  <p>Waiting for the tides...</p>
{:then data}
  {#if data._embedded}
    <div class="waves-title text-4xl mt-2">
      Hear { firstName }'s waves
    </div>
    {#each data._embedded.waves as wave}
    <div class="waves-wrapper mt-6">
      <div id={`wave-${wave.id}`} class="soundwave"></div>
      <div class="wave-info flex items-center mt-6">
        <div class:playing = {currentlyPlaying == wave.id} class="button mr-4" on:click={togglePlay(wave.id)} />
        <div class="wave-label text-xl">
          {wave.description}
        </div>
        <ReactionBlock userId = {userId} waveId={wave.id}/>
      </div>
    </div>
    {/each}
  {:else}
  <p class="waves-title text-4xl mt-2">{ userId == retrieveAuthUserId() ? 'You have not ' : firstName + ' has not '}posted any waves yet.</p>
  {/if}
{:catch error}
  <p class="hidden">This user does not have any waves yet</p>
{/await}