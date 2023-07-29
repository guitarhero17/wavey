<script>
  // import { fetchWithJwt, retrieveAuthUserId } from '../lib/userAuthentication'
  import { fade } from 'svelte/transition'
  import {url} from "@roxi/routify";

  import LoadingWave from "../components/icons/LoadingWave.svelte";
  import IconButton from "../components/edit/IconButton.svelte";
  import InfoInstrument from "../components/InfoInstrument.svelte";
  import EditableText from "../components/edit/EditableText.svelte";
  import InstrumentSelect from "../components/edit/InstrumentSelect.svelte";
  import InstrumentsCheckboxes from "../components/edit/InstrumentsCheckboxes.svelte";
  import LookingForCheckboxes from "../components/edit/LookingForCheckboxes.svelte";
  import Modal from "../components/Modal.svelte";
  import getPlayerType from "../utils/instrumentToPlayer.js";
  import capitalizeFirstLetter from "../utils/capitalizeFirstLetter.js";
  import WaveDeleteContent from "../components/modal-content/WaveDeleteContent.svelte";

  const userNonEditableFields = ['username', '_links']

  let isEditingUserDataActive = false
  let activeEditingWave = ''
  let visibleWaveDeleteModal = ''
  let showPictureUpload = false
  let playingWave = ''
  let editedUserData = {}
  let editedWaveTitles = {}

  let loadUser = async () => fetch(`/api/users/${"johny1"}`).then(res => res.json())
  let loadUserWaves = async () => fetch(`/api/users/${"johny1"}/waves`).then(res => res.json())


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
    editedUserData = {...editedUserData, [field]: newValue}
  }

  function cancelEdit() {
    isEditingUserDataActive = false
    editedUserData = {}
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
        // triggering rerender
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

  function handleEditWaveClick(waveId) {
    if (activeEditingWave === waveId) {
      updateWaveTitle(waveId)
    } else {
      activeEditingWave = waveId
    }
  }

  function handleDeleteWaveClick(waveId) {
    if (activeEditingWave === waveId) {
      editedWaveTitles = {...editedWaveTitles, [waveId]: undefined}
      activeEditingWave = ''
    } else {
      visibleWaveDeleteModal = waveId
    }
  }

  function setEditedWaveTitle(waveId, newTitle) {
    editedWaveTitles = {...editedWaveTitles, [waveId]: newTitle}
  }

  function updateWaveTitle(waveId) {
    fetch(`/api/waves/${waveId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/merge-patch+json'
      },
      body: JSON.stringify({title: editedWaveTitles[waveId]})
    }).then(res => {
      if (res.status === 204) {
        activeEditingWave = false
        editedWaveTitles = {...editedWaveTitles, waveId: undefined}
        // triggering rerender
        loadUserWaves = loadUserWaves
      } else {
        console.log('error: ', res.status)
      }
    })
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
                    <InstrumentSelect value={data[field]} on:change={i => setEditedUserField(field, i.detail)} />
                  {:else if field === 'instrumentsSecondary'}
                    <InstrumentsCheckboxes values={data[field]} on:change={i => setEditedUserField(field, i.detail)}/>
                  {:else if field === 'lookingFor'}
                    <LookingForCheckboxes values={data[field]} on:change={i => setEditedUserField(field, i.detail)}/>
                  {:else}
                    <EditableText value={data[field]} isEditingActive={isEditingUserDataActive} on:change={i => setEditedUserField(field, i.detail)} />
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
                    <EditableText value={data[field]} isEditingActive={isEditingUserDataActive} on:change={i => setEditedUserField(field, i.detail)} />
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
            <IconButton type="check" on:click={updateUserData} disabled={Object.keys(editedUserData).length === 0}/>
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
                  <p class="text-2xl">
                    <EditableText
                      value={ wave.title }
                      isEditingActive={activeEditingWave === wave.id}
                      on:change={i => setEditedWaveTitle(wave.id, i.detail)}
                      on:submit={() => updateWaveTitle(wave.id)}
                    />
                  </p>
                  <audio id={`wave-${wave.id}`} class="wave-audio">
                    <source src={$url(`../waves/${'johny1'}/${wave.fileName}`)} type="audio/mpeg"/>
                  </audio>
                  <button
                    class:playing={playingWave === wave.id}
                    class="playButton ml-4"
                    on:click={() => togglePlaying(wave.id)}
                  />
                </div>
                <div class="ml-4 s:ml-0 flex">
                  <IconButton
                    type={activeEditingWave === wave.id ? 'check' : 'edit'}
                    on:click={ () => handleEditWaveClick(wave.id) }
                    disabled={activeEditingWave === wave.id && !editedWaveTitles[wave.id]}
                    noText />
                  <div class="ml-2">
                    <IconButton
                      type="cancel"
                      on:click={() => handleDeleteWaveClick(wave.id) }
                      noText={activeEditingWave !== wave.id} />
                  </div>
                </div>
                {#if visibleWaveDeleteModal === wave.id}
                  <Modal on:close={() => visibleWaveDeleteModal = ''}>
                    <WaveDeleteContent waveTitle={wave.title} waveId={wave.id} />
                  </Modal>
                {/if}
              </li>
            {/each}
          </ul>
        </div>
      {/if}
    {/await}
    <div class="mt-6 p-4 border-2 border-waveyGreen rounded-xl">
      <p class="text-4xl">Upload a new wave</p>
    </div>
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