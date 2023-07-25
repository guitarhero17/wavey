<script>
  import UserProfile from '../../modules/UserProfile.svelte'
  import { retrieveAuthUserId } from '../../utils/userAuthentication'
</script>

<UserProfile username = { retrieveAuthUserId() }/>

