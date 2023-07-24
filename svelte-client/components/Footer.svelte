<script>
  import { url } from '@roxi/routify'
  const currentYear = new Date().getFullYear()
</script>

<footer class="group pt-10 px-5 pb-3 w-full text-center flex justify-center items-center">
  Wavey. Made with
    <img src={$url('/images/footer/heart.svg')} class="inline-block h-[24px] w-[24px] mx-[5px] transitions group-hover:scale-125 group-hover:mx-[7px]" alt="Heart Emoji"/>
  in Berlin in {currentYear}.
</footer>

<style>
  .transitions {
    transition: transform 500ms ease-in, margin 200ms ease-in;
  }
</style>