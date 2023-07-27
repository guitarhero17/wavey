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

    function onSubmit() {
        if (value !== original) {
            dispatch('submit', value)
        }
    }

    function onKeydown(e) {
        if (e.key === 'Escape') {
            e.preventDefault()
            value = original
        }
    }
</script>

{#if isEditingActive}
    <form on:keydown={onKeydown} on:change={onSubmit}>
        <input
           class="bg-black border blinking-border rounded-xl pl-2"
           bind:value={value}
           {required} />
    </form>
{:else}
    <span>
        {value}
    </span>
{/if}