<script>
  import { url } from '@roxi/routify'

  export let instrument
  export let isPrimary = false

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1);
}
</script>

<style>
  .info-instrument:not(:last-child) {
    @apply mr-5;
  }
  .primary .info-instrument-icon {
    @apply w-20;
    @apply h-20;
    @apply mr-4;
  }

  .primary .info-instrument-label {
    @apply text-3xl;
    @apply font-bold;
    @apply text-waveyYellow;
  }
</style>

<div class:primary={ isPrimary } class="info-instrument flex items-center">
  <div class="info-instrument-icon mr-2 rounded-full w-12 h-12">
    <img class="w-full" src="{$url(`/images/instruments-logos/${instrument}.svg`)}" alt="{instrument}">
  </div>
  <div class="info-instrument-label">{ capitalizeFirstLetter(instrument.toLowerCase()) }</div>
</div>