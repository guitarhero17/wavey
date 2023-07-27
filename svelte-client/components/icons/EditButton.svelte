<script>
import {url} from "@roxi/routify";
export let iconWidth = 24
export let onClick
</script>

<button on:click={onClick} class="flex border rounded p-2 hover:opacity-50 transition-opacity">
    <img width={iconWidth} height={iconWidth} src={$url('/images/icons/edit.svg')} alt="Edit Icon"/>
    <p class="ml-2">Edit</p>
</button>

<style>

</style>