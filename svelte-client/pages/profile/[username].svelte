<script>
  import UserProfile from '../../modules/UserProfile.svelte'
  // The id of the profile
  export let username
</script>

<UserProfile id={ username }/>