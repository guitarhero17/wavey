<script>
    import {instruments} from "../../utils/instruments.js";
    import capitalizeFirstLetter from "../../utils/capitalizeFirstLetter.js";
    import {createEventDispatcher, onMount} from "svelte";

    export let values = []
    let selection = []
    const dispatch = createEventDispatcher()

    onMount(() => {
        selection = values
    })

    function onInput() {
        dispatch('submit', selection)
    }
</script>

<form on:change={onInput}>
    {#each instruments as instrument}
        <label class="block">
            <input
                type="checkbox"
                value={instrument}
                bind:group={selection}>
            {capitalizeFirstLetter(instrument.toLowerCase())}
        </label>
    {/each}
</form>