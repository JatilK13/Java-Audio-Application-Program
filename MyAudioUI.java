import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			try 
			{	
				String action = scanner.nextLine();

				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;
				
				else if (action.equalsIgnoreCase("STORE"))	// List all songs
				{
					store.listAll(); 
				}
				else if (action.equalsIgnoreCase("SONGS"))	// List all songs
				{
					mylibrary.listAllSongs(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
				{
					mylibrary.listAllAudioBooks(); 
				}
				else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
				{
					mylibrary.listAllPodcasts(); 
				}
				else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
				{
					mylibrary.listAllArtists(); 
				}
				else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
				{
					mylibrary.listAllPlaylists(); 
				}
				// Download audiocontent (song/audiobook/podcast) from the store 
				// Specify the index of the content
				else if (action.equalsIgnoreCase("DOWNLOAD")) 
				{
					int index1 = 0;     // Variable to hold the first index.
					int index2 = 0;		// Variable to hold the second index.
					
					// Print a statement asking for the starting index.
					System.out.print("From Store Content #: ");

					// If the user enters an integer, assign it to the index1 variable.
					if (scanner.hasNextInt())
					{
						index1 = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())

						// Print a statement to the screen asking the user for the second index value.
						System.out.print("To Store Content #: ");

						// If the user enters an integer, assign it to the index2 variable.
						if (scanner.hasNextInt())
						{
							index2 = scanner.nextInt();
							scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt()).
						}
					}

					// Create a for loop that loops from index1 to index 2.
					for (int i = index1; i <= index2; i++)
					{
						// Create a try block.
						try
						{
							// Create an AudioContent variable called content and assign it to the audio content at index i.
							AudioContent content = store.getContent(i);

							// If the content does not exist, print an error message.
							if (content == null)
								System.out.println("Content Not Found in Store");

							// Else, execute the following code.
							else
							{
								// Call the download method with content as the parameter.
								mylibrary.download(content);

								// Print a confirmation statement to the screen.
								System.out.println(content.getType() + " " + content.getTitle() + " added to library");
							}
						}	
						// Make a catch block.
						catch (AudioContentDownloadedException x)
						{
							System.out.println(x.getMessage());
						}
				}					
				}
				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song 
				else if (action.equalsIgnoreCase("PLAYSONG")) 
				{
					// Declare an integer variable called index and initialize it to 0.
					int index = 0;

					// Print a statement asking for the song number.
					System.out.print("Song Number: ");

					// If an integer was entered, run the following code.
					if (scanner.hasNextInt())
					{
						// Assign the entered integer to the index variable.
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					// Call the playSong method with index as the parameter.
					mylibrary.playSong(index);
				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
					// Declare an integer variable called index and initialize it to 0.
					int index = 0;

					// Print a statement asking for the audio book number.
					System.out.print("Audio Book Number: ");

					// If an integer was entered, run the following code.
					if(scanner.hasNextInt())
					{
						// Assign the entered integer to the index variable.
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					// Call the printAudioBookTOC method with the parameter index.
					mylibrary.printAudioBookTOC(index);
				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter 
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{
					// Declare two integer variables called index and chapter and initialize them to 0.
					int index = 0;
					int chapter = 0;

					// Print a statement asking for the audio book number.
					System.out.print("Audio Book Number: ");

					// If an integer was entered, run the following code.
					if (scanner.hasNextInt())
					{
						// Assign the entered integer to the index variable.
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())

						// Print a statement asking for the chapter number.
						System.out.print("Chapter: ");

						// If an integer was entered, run the following code.
						if (scanner.hasNextInt())
						{
							chapter = scanner.nextInt();
							scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						}
					}
					// Call the playAudioBook method, passing it the parameters index and chapter.
					mylibrary.playAudioBook(index, chapter);
				}
				// Print the episode titles for the given season of the given podcast
				// In addition to the podcast index from the list of podcasts, 
				// read the season number from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PODTOC")) 
				{
					
				}
				// Similar to playsong above except for podcast
				// In addition to the podcast index from the list of podcasts, 
				// read the season number and the episode number from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPOD")) 
				{
					
				}
				// Specify a playlist title (string) 
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{
					// Create a string variable called title and initialize it to an empty string.
					String title = ("");

					// Print a statement asking for the playlist title.
					System.out.print("Playlist Title: ");

					// If anything is entered, run the following code.
					if (scanner.hasNextLine())
					{
						// Initialize the title variable to the input.
						title = scanner.nextLine();
					}
					// Call the playPlaylist method passing it title as the parameter.
					mylibrary.playPlaylist(title);
				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				// Play all the audio content 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					// Declare an integer variable called index and an empty string variable called title.
					int index = 0;
					String title = ("");

					// Print an output statement asking for the title of the playlist.
					System.out.print("Playlist Title: ");

					// If anything is inputted, execute the following code.
					if (scanner.hasNextLine())
					{
						// Set title to the input.
						title = scanner.nextLine();
						
						
						// Print a statement asking for the content number.
						System.out.print("Content Number: ");

						// If an integer is inputted, execute the following code.
						if(scanner.hasNextInt())
						{
							// Set index to the integer that was inputted.
							index = scanner.nextInt();
							scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						}
						// Call the playPlaylist method, passing title and index as parameters.
						mylibrary.playPlaylist(title, index);
					}
				}
				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					// Declare an integer variable called index and initialize it to 0.
					int index = 0;

					// Print a statement asking the user for library song.
					System.out.print("Library Song #: ");

					// If an integer is inputted, execute the following code.
					if (scanner.hasNextInt())
					{
						// Initialize index to the integer that was inputted.
						index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					// Call the method deleteSong and pass it index as a parameter.
					mylibrary.deleteSong(index);
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					// Create a string variable called title and initialize it to an empty string.
					String title = ("");

					// Print a statement asking for the playlist title.
					System.out.print("Playlist Title: ");

					// If anything is entered, run the following code.
					if (scanner.hasNextLine())
					{
						// Initialize the variable title to the input.
						title = scanner.nextLine();
					}
					// Call the makePlaylist method and pass it title as a parameter. 
					mylibrary.makePlaylist(title);
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
				{
					// Create a string  variable called title and initialize it to an empty string.
					String title = ("");

					// Print a statement asking for the playlist title.
					System.out.print("Playlist Title: ");

					// If anything is entered, run the following code.
					if (scanner.hasNextLine())
					{
						// Initialize the title variable to the input.
						title = scanner.nextLine();
					}
					// Call the printPlaylist method and pass it title as a parameter.
					mylibrary.printPlaylist(title);
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					// Create empty strings called title and type, and create an integer variable called index.
					String title = ("");
					String type = ("");
					int index = 0;

					// Print a statement asking for the playlist title.
					System.out.print("Playlist Title: ");
					
					// If anything is entered, run the following code.
					if (scanner.hasNextLine())
					{
						// Initialize the title variable to the input.
						title = scanner.nextLine();

						// Print a statement asking for the content type.
						System.out.print("Content Type [SONG, AUDIOBOOK]: ");

						// If anything is entered, run the following code.
						if (scanner.hasNextLine())
						{
							// Initialize the type variable to the input.
							type = scanner.nextLine();

							// Print a statement asking for the index.
							System.out.print("Library Content #: ");

							// If an integer is entered, run the following code.
							if (scanner.hasNextInt())
							{
								// Initialize the index variable to the input.
								index = scanner.nextInt();
								scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt()
							}
						}
					}
					// Call the addContentToPlaylist method, passing type, index, and title as parameters.
					mylibrary.addContentToPlaylist(type, index, title);
				}
				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{
					// Create an empty string called title and an integer variable called index.
					String title = ("");
					int index = 0;

					// Print a sentence asking for the playlist title.
					System.out.print("Playlist Title: ");

					// If input is entered, execute the following code.
					if (scanner.hasNextLine())
					{
						// Initialize title to the input.
						title = scanner.nextLine();

						// Print a sentence asking for the playlist content.
						System.out.print("Playlist Content #: ");

						// If input is entered, execute the following code.
						if (scanner.hasNextInt())
						{
							// Initialize index to the input.
							index = scanner.nextInt();
							scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						}
					}
					// Call the delContentFromPlaylist method, passing index and title as parameters.
					mylibrary.delContentFromPlaylist(index, title);
				}
				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}

				// Add a command called SEARCH.
				else if (action.equalsIgnoreCase("SEARCH"))
				{
					// Print a message to the screen asking the user for the title.
					System.out.print("Title: ");

					// Create a variable called title and initialize it to an empty string.
					String title = ("");
					
					// If the user enters anything, execute the following code.
					if (scanner.hasNextLine())
					{
						// Set the user input to the variable title.
						title = scanner.nextLine();
					}
					// Create an integer variable called index and initialize it to the return value from the 
					// getTitleIndex method from the AudioContentStore class.
					try
					{
						int index = store.getTitleIndex(title);
						System.out.print(index + 1 + ". ");		// Print out the index of the title.
						store.getContent(index+1).printInfo();	// Print the information of the song.
					}
					catch (NullPointerException x)
					{
						System.out.println("No matches for " + title);
					}
				} 

				// Add a command called SEARCHA.
				else if (action.equalsIgnoreCase("SEARCHA"))
				{
					// Print a message to the screen asking the user for the artist.
					System.out.print("Artist: ");

					// Create a variable called title and initialize it to an empty string.
					String artist = ("");

					// If the user enters anything, execute the following code.
					if (scanner.hasNextLine())
					{
						// Set the user input to the variable title.
						artist = scanner.nextLine();
					}
					
					// Create an integer ArrayList called indices and initialize it to the return value of 
					// the method getArtistIndices from the AudioContentStore class.
					ArrayList<Integer> indices = store.getArtistIndices(artist);

					// Make a try block.
					try
					{
						// Create a for loop that loops through the elements of the indices ArrayList.
						for (int index : indices)
						{
							System.out.print(index+1 + ". ");		// Print the index of the audio content.
							store.getContent(index+1).printInfo();	// Print the information of the audio content.
							System.out.println();					// Print a blank line.
						}
					}

					// Make a catch block.
					catch (NullPointerException x)
					{
						System.out.println("No matches for " + artist);
					}
				}
				
				// Add a command called SEARCHG
				else if (action.equalsIgnoreCase("SEARCHG"))
				{
					// Print a statement asking the user for a genre.
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");

					// Create an empty string called genre.
					String genre = ("");

					// If the user enters anything, execute the following code.
					if (scanner.hasNextLine())
					{
						genre = scanner.nextLine().toUpperCase();
					}

					// Create an integer ArrayList called indices and initialize it to the return value of the method getGenreIndices from te audio content store.
					ArrayList<Integer> indices = store.getGenreIndices(genre);

					// Make a try block.
					try
					{
						// Create a for loop that loops through the indices list.
						for (int index : indices)
						{
							System.out.print(index+1 + ". ");		// Print the index of the audio content.
							store.getContent(index+1).printInfo();	// Print the information of the audio content.
							System.out.println();					// Print a blank line.
						}
					}
					
					// Make a catch block.
					catch (NullPointerException x)
					{
						System.out.println("No matches for " + genre);
					}
				}

				// Add a command called DOWNLOADA
				else if (action.equalsIgnoreCase("DOWNLOADA"))
				{
					// Print a message to the screen asking the user for the artist name.
					System.out.print("Artist Name: ");

					// Create an empty string called artistName.
					String artistName = ("");

					// If the user enters anything, execute the following code.
					if (scanner.hasNextLine())
					{
						// initialize artistName to the input.
						artistName = scanner.nextLine();
					}

					// Create an integer ArrayList and initialize it to the corresponding ArrayList using the getArtistIndices method.
					ArrayList<Integer> indices = store.getArtistIndices(artistName);

					// Make a try block.
					try 
					{
						// Create a for loop to loop through the indices list.
						for (int index : indices)
						{
							// Make a try block
							try
							{
								// Call the download method with content as the parameter.
								mylibrary.download(store.getContent(index+1));
								System.out.println(store.getContent(index+1).getType() + " " + store.getContent(index+1).getTitle() + " added to library");
							}
							// Make a catch block.
							catch (AudioContentDownloadedException x)
							{
								System.out.println(x.getMessage());
							}
						}
					}
					// Make a catch block
					catch (NullPointerException x)
					{
						System.out.println("No matches for " + artistName);
					}
				}
				
				// Add a command called DOWNLOADG
				else if (action.equalsIgnoreCase("DOWNLOADG"))
				{
					// Print a statement asking the user to enter a genre.
					System.out.print("Genre: ");

					// Create an empty string called genre.
					String genre = ("");

					// If the user enters anything, initialize the genre variable to the input.
					if (scanner.hasNextLine())
					{
						genre = scanner.nextLine().toUpperCase();
					}

					// Create an integer ArrayList and initialize it to the corresponding ArrayList using the getGenreIndices method.
					ArrayList<Integer> indices = store.getGenreIndices(genre);

					// Make a try block.
					try 
					{
						// Create a for loop to loop through the indices list.
						for (int index : indices)
						{
							// Make a try block
							try
							{
								// Call the download method with content as the parameter.
								mylibrary.download(store.getContent(index+1));
								System.out.println(store.getContent(index+1).getType() + " " + store.getContent(index+1).getTitle() + " added to library");
							}
							// Make a catch block.
							catch (AudioContentDownloadedException x)
							{
								System.out.println(x.getMessage());
							}
						}
					}
					// Make a catch block
					catch (NullPointerException x)
					{
						System.out.println("No matches for " + genre);
					}
				}
			}

			// Catch AudioContentDownloadedException.
			catch (AudioContentDownloadedException x)
			{
				System.out.println(x.getMessage());
			}

			// Catch AudioContentNotFoundException.
			catch (AudioContentNotFoundException x)
			{
				System.out.println(x.getMessage());
			}

			// Catch ChapterNotFoundException.
			catch (ChapterNotFoundException x)
			{
				System.out.println(x.getMessage());
			}

			// Catch PlaylistAlreadyExistsException.
			catch (PlaylistAlreadyExistsException x)
			{
				System.out.println(x.getMessage());
			}

			// Catch PlaylistNotFoundException.
			catch (PlaylistNotFoundException x)
			{
				System.out.println(x.getMessage());
			}

			// Catch InvalidTypeException.
			catch (InvalidTypeException x)
			{
				System.out.println(x.getMessage());
			}
				System.out.print("\n>");
		}

	}
}
