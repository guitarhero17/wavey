<script>
import {url} from "@roxi/routify";
import {createEventDispatcher} from "svelte";

export let type = 'edit'
export let iconWidth = 24
const dispatch = createEventDispatcher()

const buttons = {
    edit: {
        text: 'Edit',
        imagePath: '/images/icons/edit.svg'
    },
    save: {
        text: 'Save',
        imagePath: '/images/icons/check.svg'
    },
    cancel: {
        text: 'Cancel',
        imagePath: '/images/icons/cancel.svg'
    }
}

function onClick() {
    dispatch('click')
}
</script>

<button on:click={onClick} class="flex justify-center w-28 border rounded py-2 hover:opacity-50 transition-opacity">
    <img width={iconWidth} height={iconWidth} src={$url(buttons[type].imagePath)} alt="Edit Icon"/>
    <p class="ml-2">{buttons[type].text}</p>
</button>