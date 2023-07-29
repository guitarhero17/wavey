<script>
import {url} from "@roxi/routify";
import {createEventDispatcher} from "svelte";

export let type = 'edit'
export let iconWidth = 24
export let disabled = false
export let noText = false
const dispatch = createEventDispatcher()

const buttons = {
    edit: {
        text: 'Edit',
        imagePath: '/images/icons/edit.svg'
    },
    check: {
        text: 'Save',
        imagePath: '/images/icons/check.svg'
    },
    cancel: {
        text: 'Cancel',
        imagePath: '/images/icons/cancel.svg'
    },
}

function onClick() {
    dispatch('click')
}
</script>

<button
    on:click={onClick}
    class:w-28={!noText}
    class:border-red-500={type === 'cancel'}
    class:text-red-500={type === 'cancel'}
    class="p-2 flex justify-center border rounded transition-transform disabled:opacity-50 hover:scale-110 disabled:hover:scale-100"
    {disabled}>
    <svg width={iconWidth} height={iconWidth}>
        <use href={`${$url(buttons[type].imagePath)}#${type}`}></use>
    </svg>
    {#if !noText}
        <p class="ml-2">{buttons[type].text}</p>
    {/if}
</button>

<style>

</style>