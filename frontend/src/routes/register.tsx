import { A } from "@solidjs/router";
import { Button } from "~/components/ui/button";

export default function register() {
	const handleSubmit = (e: Event) => {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const email = formData.get("email") as string;
		sessionStorage.setItem("email", email);
	};

	return (
		<div class="min-h-screen flex flex-col">
			<div class="flex-1 flex flex-col items-center justify-center">
				<h1 class="text-5xl mb-10">Register</h1>
				<div class="bg-purple-400 rounded-3xl p-10 w-[500px]">
					<form class="flex flex-col gap-6" onSubmit={handleSubmit}>
						<div class="flex flex-col gap-2">
							<label for="email" class="text-sm text-black">
								Email
							</label>
							<input
								id="email"
								type="email"
								placeholder="email"
								class="bg-white rounded-lg px-4 py-2 text-sm border-none outline-none"
							/>
						</div>
						<div class="flex flex-col gap-2">
							<label for="password" class="text-sm text-black">
								Password
							</label>
							<input
								id="password"
								type="password"
								placeholder="password"
								class="bg-white rounded-lg px-4 py-2 text-sm border-none outline-none"
							/>
						</div>
						<div class="flex gap-3 justify-center items-center flex-col">
							<Button
								type="submit"
								class="hover:bg-black/75 rounded-lg px-6 w-full"
							>
								register
							</Button>
							<A href="/login" class="hover:underline">
								login instead
							</A>
						</div>
					</form>
				</div>
			</div>
		</div>
	);
}
