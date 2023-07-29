<script>
    import { createEventDispatcher, onMount } from 'svelte'

    export let value
    export let required = false
    export let isEditingActive = false

    const dispatch = createEventDispatcher()
    let original

    onMount(() => {
        original = value
    })

    function onInput(e) {
        console.log(e.target.value)
        dispatch('change', e.target.value)
    }

    function onKeydown(e) {
        if (e.key === 'Enter') {
            dispatch('submit')
        }
    }
</script>

{#if isEditingActive}
    <form on:submit|preventDefault>
        <input
           class="bg-black border blinking-border rounded-xl pl-2"
           type="text"
           value={value}
           on:input={onInput}
           on:keydown={onKeydown}
           {required} />
    </form>
{:else}
    <span>
        {value}
    </span>
{/if}