import { A } from "@solidjs/router";

export default function NotFound() {
	return (
		<main class="flex justify-center items-center h-screen">
			<A href="/" class="hover:underline">
				404 :)
			</A>
		</main>
	);
}
