<script>
    import capitalizeFirstLetter from "../../utils/capitalizeFirstLetter.js";
    import {createEventDispatcher, onMount} from "svelte";
    import {instruments} from "../../utils/instruments.js";

    export let value
    let original
    const dispatch = createEventDispatcher()

    onMount(() => {
        original = value
    })

    function onChange() {
        dispatch('submit', value)
    }
</script>

<select
    class="bg-black border blinking-border rounded-xl pl-2"
    bind:value={value}
    on:change={onChange}
>
    {#each instruments as instrument}
        <option value={instrument}>{capitalizeFirstLetter(instrument.toLowerCase())}</option>
    {/each}
</select>