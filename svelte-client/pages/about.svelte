<style>
	h1 { 
    color: purple;
    border: 1px solid green;
	}
</style>
<script>
import { afterPageLoad } from "@roxi/routify"

$afterPageLoad(page => {
  console.log('loaded ' + page.title)
})
</script>

<h1 class="text-2xl">This is about</h1>