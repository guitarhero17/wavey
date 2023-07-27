<script>
  // import { fetchWithJwt, retrieveAuthUserId } from '../lib/userAuthentication'
  import { slide, fade } from 'svelte/transition'
  import UploadFile from '../components/UploadFile.svelte'
  import LoadingWave from "../components/icons/LoadingWave.svelte";
  import {url} from "@roxi/routify";
  import EditButton from "../components/icons/EditButton.svelte";
  import capitalizeFirstLetter from "../utils/capitalizeFirstLetter.js";
  import InfoInstrument from "../components/InfoInstrument.svelte";
  import getPlayerType from "../utils/instrumentToPlayer.js";

  let showPictureUpload = false
  let playingWave = ''

  const loadUser = async () => fetch(`/api/users/${"johny1"}`).then(res => res.json())
  const loadUserWaves = async () => fetch(`/api/users/${"johny1"}/waves`).then(res => res.json())

  const userNonEditableFields = ['username', '_links']

  function togglePlaying(waveId) {
    if (playingWave !== waveId) {
      document.querySelectorAll('.wave-audio').forEach(el => el.pause())
      document.getElementById(`wave-${waveId}`).play()
      playingWave = waveId
    } else {
      document.getElementById(`wave-${waveId}`).pause()
      playingWave = ''
    }
  }

  // async function getWavesNames() {
  //   let wavesNames = []
  //   let response = await fetchWithJwt(
  //     `/api/users/${ retrieveAuthUserId()}/waves`)
  //   let dataEmbedded = response._embedded
  //
  //   if (dataEmbedded) {
  //     wavesNames = dataEmbedded.waves.map(wave => wave.description)
  //   }
  //   // console.log(wavesNames)
  //   return wavesNames
  // }


</script>

<article class="edit-profile px-5">
  {#await loadUser()}
    <p class="text-4xl">Loading your data...</p>
    <div transition:fade class="absolute w-full flex justify-center p-8">
      <LoadingWave />
    </div>
  {:then data}
    <div class="mt-6 flex justify-between items-center s:flex-col-reverse s:items-start p-4 border-2 border-waveyGreen rounded-xl">
      <div class="s:mt-6">
        {#each Object.keys(data).filter(k => !userNonEditableFields.includes(k)) as field}
          <div class="edit-field-block">
            <p class="text-3xl">
              {#if field === 'email'}
                E-Mail
              {:else if field === 'instrumentPrimary'}
                Primary instrument
              {:else if field === 'instrumentsSecondary'}
                Secondary instruments
              {:else if field === 'lookingFor'}
                Looking For
              {:else}
                {capitalizeFirstLetter(field)}
              {/if}
            </p>
            <div class="flex items-center mt-2">
              <div class="text-2xl">
                {#if field === 'instrumentPrimary'}
                  <InfoInstrument instrument={data[field]} isPrimary/>
                {:else if field === 'instrumentsSecondary'}
                  {#each data[field] as secondaryInstrument}
                    <div class="secondary-instrument">
                      <InfoInstrument instrument={secondaryInstrument} />
                    </div>
                  {/each}
                {:else if field === 'lookingFor'}
                  {#each data[field] as lfField}
                   <div class="text-xl mb-2">{ getPlayerType(lfField) }</div>
                  {/each}
                {:else}
                  {data[field]}
                {/if}
              </div>
            </div>
          </div>
        {/each}
    </div>
      <div class="flex-grow flex justify-center">
        <EditButton onClick={() => console.log('hey')}/>
      </div>
    </div>
  {/await}
<!--  <div class="change-picture">-->
<!--    <button on:click = { () => showPictureUpload = true }>Change my picture</button>-->
<!--    {#if showPictureUpload}-->
<!--      <div transition:slide >-->
<!--        <UploadFile/>-->
<!--      </div>-->
<!--    {/if}-->
<!--  </div>-->
  <div class="mt-6">
    {#await loadUserWaves()}
      <p class="text-4xl">Looking for waves...</p>
      <div transition:fade class="absolute w-full flex justify-center p-8">
        <LoadingWave />
      </div>
    {:then data}
      {#if data._embedded}
        <div class="p-4 border-2 border-waveyGreen rounded-xl">
          <p class="text-4xl">Edit your waves</p>
          <ul class="pt-8">
        {#each data._embedded.waves as wave}
          <li class="wave flex items-center s:flex-col-reverse s:items-start">
            <div class="flex items-center">
              <p class="text-2xl">{ wave.title }</p>
              <audio id={`wave-${wave.id}`} class="wave-audio">
                <source src={$url(`../waves/${'johny1'}/${wave.fileName}`)} type="audio/mpeg"/>
              </audio>
              <button
                class:playing={playingWave === wave.id}
                class="playButton ml-4"
                on:click={() => togglePlaying(wave.id)}
              />
            </div>
            <div class="ml-2 s:ml-0">
              <EditButton onClick={() => console.log('hey')} />
            </div>
          </li>
        {/each}
        </ul>
        </div>
      {/if}
    {/await}
    <div class="upload-waves"></div>
  </div>
</article>

<style>
  .edit-field-block + .edit-field-block {
    @apply mt-8;
  }

  .secondary-instrument + .secondary-instrument {
    @apply mt-4;
  }

  .wave + .wave {
    @apply mt-5;
  }
</style>