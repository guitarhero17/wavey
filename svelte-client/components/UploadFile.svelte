<script>
import { fetchWithJwt, retrieveAuthUserId } from '../lib/userAuthentication';

  let files
  let value = "yes"
  let dataFile = null

  function upload() {
    const formData = new FormData()
    formData.append('damName', value)
    formData.append('image', files[0])
    fetchWithJwt(`https://localhost:8443/users/${retrieveAuthUserId()}/picture`, 'POST', formData)
      .then((result) => {
        // console.log('Success:', result)
      })
      .catch((error) => {
        console.error('Error:', error)
      })
  }
</script>

<input id="fileUpload" type="file" bind:files />

{#if dataFile && files[0]}
  <p>
    {files[0].name}
  </p>
{/if}

{#if value}
  <button on:click={upload}>Submit</button>
{:else}
  <button on:click={upload} disabled>Submit</button>
{/if}
