<script>
  // import { fetchWithJwt, retrieveAuthUserId } from '../lib/userAuthentication'
  import { slide, fade } from 'svelte/transition'
  import UploadFile from '../components/UploadFile.svelte'
  import LoadingWave from "../components/icons/LoadingWave.svelte";
  import {url} from "@roxi/routify";
  import IconButton from "../components/edit/IconButton.svelte";
  import capitalizeFirstLetter from "../utils/capitalizeFirstLetter.js";
  import InfoInstrument from "../components/InfoInstrument.svelte";
  import getPlayerType from "../utils/instrumentToPlayer.js";
  import EditableText from "../components/edit/EditableText.svelte";
  import InstrumentSelect from "../components/edit/InstrumentSelect.svelte";
  import InstrumentsCheckboxes from "../components/edit/InstrumentsCheckboxes.svelte";
  import LookingForCheckboxes from "../components/edit/LookingForCheckboxes.svelte";

  const userNonEditableFields = ['username', '_links']
  let isEditingUserDataActive = false
  let showPictureUpload = false
  let playingWave = ''

  let editedUserData = {}
  $: console.log(editedUserData)

  let loadUser = async () => fetch(`/api/users/${"johny1"}`).then(res => res.json())
  const loadUserWaves = async () => fetch(`/api/users/${"johny1"}/waves`).then(res => res.json())


  function getEditableFieldLabel (field) {
    switch(field) {
      case 'email':
        return 'E-Mail'
      case 'instrumentPrimary':
        return 'Primary instrument'
      case 'instrumentsSecondary':
        return 'Secondary instruments'
      case 'lookingFor':
        return 'Looking For'
      default:
        return capitalizeFirstLetter(field)
    }
  }

  function setEditedUserField(field, newValue) {
    editedUserData[field] = newValue
    editedUserData = editedUserData
  }

  function cancelEdit() {
    isEditingUserDataActive = false
  }

  function updateUserData() {
    fetch(`/api/users/${'johny1'}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/merge-patch+json'
      },
      body: JSON.stringify(editedUserData)
    }).then(res => {
      if (res.status === 204) {
        isEditingUserDataActive = false
        editedUserData = {}
        loadUser = loadUser
      } else {
        console.log('error: ', res.status)
      }
    })
  }

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
</script>

<article class="edit-profile px-5">
  {#await loadUser()}
    <p class="text-4xl">Loading your data...</p>
    <div transition:fade class="absolute w-full flex justify-center p-8">
      <LoadingWave />
    </div>
  {:then data}
    <div class:blinking-border={isEditingUserDataActive} class="mt-6 flex justify-between items-start s:flex-col-reverse p-4 border-2 border-waveyGreen rounded-xl">
      <div class="s:mt-6">
        {#if isEditingUserDataActive}
          {#each Object.keys(data).filter(k => !userNonEditableFields.includes(k)) as field}
            <div class="edit-field-block">
              <p class="text-3xl">
                {getEditableFieldLabel(field)}
              </p>
              <div class="flex items-center mt-2">
                <div class="text-2xl pl-4">
                  {#if field === 'instrumentPrimary'}
                    <InstrumentSelect value={data[field]} on:submit={i => setEditedUserField(field, i.detail)} />
                  {:else if field === 'instrumentsSecondary'}
                    <InstrumentsCheckboxes values={data[field]} on:submit={i => setEditedUserField(field, i.detail)}/>
                  {:else if field === 'lookingFor'}
                    <LookingForCheckboxes values={data[field]} on:submit={i => setEditedUserField(field, i.detail)}/>
                  {:else}
                    <EditableText value={data[field]} isEditingActive={isEditingUserDataActive} on:submit={i => setEditedUserField(field, i.detail)} />
                  {/if}
                </div>
              </div>
            </div>
          {/each}
        {:else}
          {#each Object.keys(data).filter(k => !userNonEditableFields.includes(k)) as field}
            <div class="edit-field-block">
              <p class="text-3xl">
                {getEditableFieldLabel(field)}
              </p>
              <div class="flex items-center mt-2">
                <div class="text-2xl pl-4">
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
                    <EditableText value={data[field]} isEditingActive={isEditingUserDataActive} on:submit={i => setEditedUserField(field, i.detail)} />
                  {/if}
                </div>
              </div>
            </div>
          {/each}
        {/if}
    </div>
      <div class="flex-grow flex justify-center">
        {#if isEditingUserDataActive}
          <div>
            <IconButton type="save" on:click={updateUserData}/>
          </div>
          <div class="ml-4">
            <IconButton type="cancel" on:click={cancelEdit}/>
          </div>
        {:else}
          <IconButton on:click={() => (isEditingUserDataActive = true)}/>
        {/if}
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
              <IconButton onClick={() => console.log('hey')} />
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