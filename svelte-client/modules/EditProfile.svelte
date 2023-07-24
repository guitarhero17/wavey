<script>
  // import { fetchWithJwt, retrieveAuthUserId } from '../lib/userAuthentication'
  import { slide } from 'svelte/transition'
import UploadFile from '../components/UploadFile.svelte'

  let showPictureUpload = false

  async function getWavesNames() {
    let wavesNames = []
    // let response = await fetchWithJwt(
    //   `/api/users/${ retrieveAuthUserId()}/waves`)
    // let dataEmbedded = response._embedded

    // if (dataEmbedded) {
    //   wavesNames = dataEmbedded.waves.map(wave => wave.description)
    // }
    // // console.log(wavesNames)
    return wavesNames
  }


</script>

<article class="edit-profile px-5">
  <div class="change-picture">
    <button on:click = { () => showPictureUpload = true }>Change my picture</button>
    {#if showPictureUpload}
      <div transition:slide >
        <UploadFile/>
      </div>
    {/if}
  </div>
  <div class="change-instrumentPrimary">

  </div>
  <div class="change-instrumentsSecondary">
    
  </div>
  <div class="change-lookingFor">
    
  </div>
  <div class="change-waves">
    <!-- {#await fetchWithJwt(`/api/users/${ retrieveAuthUserId() }/waves`)}
      <p>Looking for waves...</p>
    {:then response}
      {#if response._embedded}
        <ul class="list-waves">
        {#each response._embedded.waves.map(wave => wave.description) as waveName}
          <li class="wave">{ waveName }</li>
        {/each}
        </ul>
      {:else}
        <p>No waves yet</p>
      {/if}
    {/await} -->
    <!-- {#if getWavesNames()}
      <ul class="list-waves">
        {#each getWavesNames() as waveName}
          <li class="wave">{ waveName }</li>
        {/each}
      </ul>
    {/if} -->
    <div class="upload-waves"></div>
  </div>
</article>