import { createSignal } from "solid-js";
import { useAuth } from "../auth-provider";
import { Avatar, AvatarFallback, AvatarImage } from "./ui/avatar";
import { Button } from "./ui/button";

export default function Nav() {
	const [isDropdownOpen, setIsDropdownOpen] = createSignal(false);

	const toggleDropdown = () => {
		setIsDropdownOpen(!isDropdownOpen());
	};

	const getInitials = () => {
		const email = sessionStorage.getItem("email") || "U";
		return email.charAt(0).toUpperCase();
	};

	return (
		<header class="bg-purple-950 text-white py-6 px-8 flex justify-between items-center">
			<a class="text-4xl font-bold" href="/">
				Gamble like a Boss
			</a>
			<div class="flex gap-8 items-center">
				<a href="/discover">
					<Button class="text-white" variant="link">
						Discover
					</Button>
				</a>
				<a href="/create">
					<Button class="text-white" variant="link">
						Create
					</Button>
				</a>
				<div class="relative dropdown-container">
					<Avatar
						class="cursor-pointer hover:ring-2 hover:ring-white transition-all bg-white"
						onClick={toggleDropdown}
					>
						<AvatarFallback class="text-black">{getInitials()}</AvatarFallback>
					</Avatar>
					<div
						class={`absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg transition-all duration-200 z-10 ${isDropdownOpen() ? "opacity-100 visible" : "opacity-0 invisible"}`}
					>
						<Button
							variant="ghost"
							onClick={() => {
								sessionStorage.removeItem("email");
								window.location.replace("/login");
							}}
							class="w-full justify-start text-black hover:bg-gray-300 active:bg-gray-400"
						>
							Logout
						</Button>
					</div>
				</div>
			</div>
		</header>
	);
}
