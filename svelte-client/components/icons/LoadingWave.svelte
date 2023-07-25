<script>
  export let size = 75
  export let animationDuration = 1000
</script>

<div class="spinner" style={`--size: ${size}px; --animationDuration: ${animationDuration}ms`}>
  {#each Array(5) as _}
    <div class="spinner-item"></div>
  {/each}
</div>

<style>
  @keyframes spinner1 {
    50% {
      transform: scaleY(0.25);
    }
  }

  .spinner {
    width: var(--size);
    height: var(--size);
    display: flex;
    align-items: center;
    justify-content: space-evenly;
  }

  .spinner-item {
		width: calc(var(--size) / 12);
		height: 80%;
		background: white;
		animation: spinner1 var(--animationDuration) ease-in-out infinite;
	}

  .spinner-item:nth-child(1) {
		animation-delay: calc(var(--animationDuration) / 10 * -3);
	}

	.spinner-item:nth-child(2) {
		animation-delay: calc(var(--animationDuration) / 10 * -1);
	}

	.spinner-item:nth-child(3) {
		animation-delay: calc(var(--animationDuration) / 10 * -2);
	}

	.spinner-item:nth-child(4) {
		animation-delay: calc(var(--animationDuration) / 10 * -1);
	}

	.spinner-item:nth-child(5) {
		animation-delay: calc(var(--animationDuration) / 10 * -3);
	}
</style>