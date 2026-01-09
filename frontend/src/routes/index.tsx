import { A, redirect } from "@solidjs/router";
import { Button } from "~/components/ui/button";
import { useAuth } from "../auth-provider";

export default function Home() {
	const auth = useAuth();

	if (!auth.isAuthenticated()) {
		redirect("/login");
	}

	const handleClearSession = () => {
		sessionStorage.removeItem("email");
		window.location.href = "/login";
	};

	return (
		<div class="min-h-screen flex items-center justify-center pt-20">
			<div class="text-center space-y-8">
				<h2 class="text-4xl font-bold mb-4">Welcome!</h2>
				<div class="flex flex-col gap-4 items-center">
					<A href="/discover" class="w-64">
						<Button variant="default" size="lg" class="w-full">
							Discover
						</Button>
					</A>
					<A href="/create" class="w-64">
						<Button variant="default" size="lg" class="w-full">
							Create
						</Button>
					</A>
					<A href="/about" class="w-64">
						<Button variant="default" size="lg" class="w-full">
							About
						</Button>
					</A>
					<Button
						variant="destructive"
						size="lg"
						class="w-64 text-muted-foreground hover:text-destructive"
						onClick={handleClearSession}
					>
						Logout
					</Button>
				</div>
			</div>
		</div>
	);
}
