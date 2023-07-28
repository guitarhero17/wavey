<script>
    import {createEventDispatcher, onMount} from "svelte";

    let dialog; // HTMLDialogElement
    let closing = false
    let windowScrollY

    const dispatch = createEventDispatcher()

    function initiateClosing() {
        closing = true
    }

    function closeDialog(e) {
        if (e.animationName.includes('fadeOut') && !e.pseudoElement) {
            document.body.classList.remove('fixed')
            document.body.style.top = ''
            window.scrollTo(0, parseInt(windowScrollY ?? '0'))
            closing = false
            dialog.close() // triggers "close" event and dispatches "close" to the parent
        }
    }

    onMount(() => {
        windowScrollY = window.scrollY
        // global class
        document.body.classList.add('fixed')
        document.body.style.top = `-${windowScrollY}px`
        dialog.addEventListener('close-dialog', initiateClosing)
        dialog.showModal()
    })
</script>

<dialog
    class:closing
    class="relative max-w-[40rem] bg-transparent"
    bind:this={dialog}
    on:close={() => dispatch('close')}
    on:click|self={initiateClosing}
    on:animationend={e => closeDialog(e)}
>
    <div class="bg-gray-800 text-white rounded-3xl mx-4">
        <div
                class="px-16 h-[30rem] grid place-items-center s:pt-10 s:px-10 overflow-hidden"
                on:click|stopPropagation
        >
            <button
                class="custom-shadow-waveyYellow close-button bg-transparent before:bg-waveyYellow after:bg-waveyYellow block absolute top-5 right-5 w-6 h-6 rounded-full hover:scale-125"
                autofocus
                on:click={initiateClosing}></button>
            <slot onClose={initiateClosing} />
        </div>
    </div>
</dialog>

<style>
    dialog[open] {
        animation: fadeIn 400ms;
    }

    dialog[open]::backdrop {
        background-color: rgba(0, 0, 0, 0.66);
        animation: fadeIn 400ms;
    }

    dialog.closing {
        animation: fadeOut 400ms;
    }

    dialog.closing::backdrop {
        animation: fadeOut 400ms;
    }

    .close-button {
        transition: transform 200ms ease-in, background 200ms ease-in;
    }

    .close-button::before, .close-button::after {
        @apply block absolute top-1/2 left-1 w-4 h-px origin-center;
        transition: height 200ms ease-in, background 200ms ease-in;
    }

    .close-button::before:hover, .close-button::after:hover {
        @apply h-[2px];
    }

    .close-button::before {
        transform: translate(0, -50%) rotate(45deg);
    }

    .close-button::after {
        transform: translate(0, -50%) rotate(-45deg);
    }

    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }

    @keyframes fadeOut {
        to {
            opacity: 0;
        }
    }
</style>