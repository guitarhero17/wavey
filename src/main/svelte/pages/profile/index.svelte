<script>
  import UserProfile from '../../modules/UserProfile.svelte'
  import { retrieveAuthUserId } from '../../lib/userAuthentication'
</script>

<UserProfile userId = { retrieveAuthUserId() }/>

