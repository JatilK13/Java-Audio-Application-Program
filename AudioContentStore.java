import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private Map<String, Integer> titles;
		private Map<String, ArrayList<Integer>> artists;
		private Map<String, ArrayList<Integer>> genres;
		
		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();
			
			try
			{
				contents = storeReader();
			}

			catch(IOException x)
			{
				System.out.println(x.getMessage());
				System.exit(1);
			}
			
			// Create a podcast object if you are doing the bonus see the makeSeasons() method below
			// It is currently commented out. It makes use of a class Season you may want to also create
			// or change it to something else
					
			// Initialize the titles map.
			titles = new HashMap<String, Integer>();

			// Create a for loop to loop as many times as there are elements in the contents list.
			for (int i = 0; i < contents.size(); i++)
			{
				// Set the key and index of the map for each iteration.
				titles.put(contents.get(i).getTitle(), i);
			}

			// Initialize the artists map.
			artists = new HashMap<String, ArrayList<Integer>>();

			// Create a for loop that loops through the contents list.
			for (AudioContent element : contents)
			{
				// Create an integer array list called indices.
				ArrayList<Integer> indices = new ArrayList<Integer>();

				// If element is a song, execute the following code.
				if (element.getType().equals(Song.TYPENAME))
				{
					// Type cast element as a Song object and get the artist.
					String artist = ((Song)element).getArtist();

					// Create a for loop that loops as many times as there are elements in the contents list.
					for (int i = 0; i < contents.size(); i++)
					{
						// If the element of contents at index i is a song, execute the following code.
						if (contents.get(i).getType().equals(Song.TYPENAME))
						{
							// If artist is equal to the element of contents at index i, add i to the indices list.
							if (((Song)contents.get(i)).getArtist().equals(artist))
							{
								indices.add(i);
							}
						}
					}
					// Put the artist and the indices list into the map.
					artists.put(artist, indices);
				}
				// Else if element is an audiobook, execute the following code.
				else if (element.getType().equals(AudioBook.TYPENAME))
				{
					// Type cast element as an AudioBook object and get the author.
					String author = ((AudioBook)element).getAuthor();	
					
					// Create a for loop that loops as many times as there are elements in the contents list.
					for (int i = 0; i < contents.size(); i++)
					{
						// If the element of contents at index i is a song, execute the following code.
						if (contents.get(i).getType().equals(AudioBook.TYPENAME))
						{
							// If author is equal to the element of contents at index i, add i to the indices list.
							if (((AudioBook)contents.get(i)).getAuthor().equals(author))
							{
								indices.add(i);
							}
						}
					}
					// Put the author and the indices list into the map.
					artists.put(author, indices);
				}
			}

			// Initialize the genres map.
			genres = new HashMap<String, ArrayList<Integer>>();

			// Declare and initialize integer ArrayLists for each genre of songs.
			ArrayList<Integer> popIndices = new ArrayList<Integer>();
			ArrayList<Integer> rockIndices = new ArrayList<Integer>();
			ArrayList<Integer> jazzIndices = new ArrayList<Integer>();
			ArrayList<Integer> hiphopIndices = new ArrayList<Integer>();
			ArrayList<Integer> rapIndices = new ArrayList<Integer>();
			ArrayList<Integer> classicalIndices = new ArrayList<Integer>();

			// Create a for loop that loops as many times as there are elements in the contents list
			for (int i = 0; i < contents.size(); i++)
			{
				// If the element of contents at index i is a song, execute the following code.
				if (contents.get(i).getType().equals(Song.TYPENAME))
				{
					// Type cast the element of contents at index i to a song object.
					Song currentSong = (Song)contents.get(i);

					// If the element of contents at index i is a pop song, add i to the popIndices ArrayList.
					if (currentSong.getGenre().equals(Song.Genre.POP))
					{
						popIndices.add(i);
					}

					// If the element of contents at index i is a rock song, add i to the popIndices ArrayList.
					else if (currentSong.getGenre().equals(Song.Genre.ROCK))
					{
						rockIndices.add(i);
					}

					// If the element of contents at index i is a jazz song, add i to the popIndices ArrayList.
					else if (currentSong.getGenre().equals(Song.Genre.JAZZ))
					{
						jazzIndices.add(i);
					}

					// If the element of contents at index i is a hiphop song, add i to the popIndices ArrayList.
					else if (currentSong.getGenre().equals(Song.Genre.HIPHOP))
					{
						hiphopIndices.add(i);
					}

					// If the element of contents at index i is a rap song, add i to the popIndices ArrayList.
					else if (currentSong.getGenre().equals(Song.Genre.RAP))
					{
						rapIndices.add(i);
					}

					// If the element of contents at index i is a classical song, add i to the popIndices ArrayList.
					else if (currentSong.getGenre().equals(Song.Genre.CLASSICAL))
					{
						classicalIndices.add(i);
					}
				}
			}
			// Put information into the map using each of the genres and their indices ArrayLists.
			genres.put("POP", popIndices);
			genres.put("ROCK", rockIndices);
			genres.put("JAZZ", jazzIndices);
			genres.put("HIPHOP", hiphopIndices);
			genres.put("RAP", rapIndices);
			genres.put("CLASSICAL", classicalIndices);
		}

		private ArrayList<AudioContent> storeReader() throws IOException
		{
			// Create a file object called store that holds the store.txt file
			File store = new File("store.txt");

			// Create a scanner object called scan that will read through the store contents.
			Scanner scan = new Scanner(store);

			// Create a while loop that loops as long as there is another line in the store file.
			while (scan.hasNextLine())
			{
				// Declare variables that all audio contents have in common.
				String title;			
				int year; 				
				String id;				
				String type;	 
				int length;

				// Declare variables specific for songs.
				String artist;
				String composer;
				String genre;
				String lyrics = ("");
				int numLines;

				// Declare variables specific for audiobooks.
				String author;
				String narrator;
				ArrayList<String> chapterTitles = new ArrayList<String>();
				ArrayList<String> chapters = new ArrayList<String>();
				int numChapters;
				int chapterLines;

				// Read the first line (the type) of the store file.
				type = scan.nextLine();

				// If the content is a song, execute the following code.
				if (type.equals("SONG"))
				{
					id = scan.nextLine();       // Initialize id to the next line of the store file.
					title = scan.nextLine();	// Initialize title to the next line of the store file.
					year = scan.nextInt();		// Initialize year to the next integer in the store file.
					scan.nextLine();			// Proceed to the next line after reading the integer.
					length = scan.nextInt();    // Initialize length to the next integer in the store file.
					scan.nextLine();            // Proceed to the next line after reading the integer.
					artist = scan.nextLine();   // Initialize artist to the next line in the store file.
					composer = scan.nextLine(); // Initialize composer to the next line in the store file.
					genre = scan.nextLine();    // Initialize genre to the next line in the store file.
					numLines = scan.nextInt();  // Initialize numLines to the next integer in the store file.
					scan.nextLine();            // Proceed to the next line after reading the integer.

					// Create a for loop to loop as many times as there are lines of lyrics in the song.
					for (int i = 0; i < numLines; i++)
					{
						// Initialize lyrics to the next line in the store file and then skip a line.
						lyrics += (scan.nextLine() + "\n");
					}

					// If the genre is pop, add a new song with the title, year, id, type, lyrics, length, artist, composer, genre (as POP), and lyrics to the contents list.
					if (genre.equals("POP"))
					{
						contents.add(new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.POP, lyrics));
					}

					// Else if the genre is ROCK, add a new song with the title, year, id, type, lyrics, length, artist, composer, genre (as ROCK), and lyrics to the contents list.
					else if (genre.equals("ROCK"))
					{
						contents.add(new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.ROCK, lyrics));
					}

					// Else if the genre is JAZZ, add a new song with the title, year, id, type, lyrics, length, artist, composer, genre (as JAZZ), and lyrics to the contents list.
					else if (genre.equals("JAZZ"))
					{
						contents.add(new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.JAZZ, lyrics));
					}

					// Else if the genre is HIPHOP, add a new song with the title, year, id, type, lyrics, length, artist, composer, genre (as HIPHOP), and lyrics to the contents list.
					else if (genre.equals("HIPHOP"))
					{
						contents.add(new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.HIPHOP, lyrics));
					}

					// Else if the genre is RAP, add a new song with the title, year, id, type, lyrics, length, artist, composer, genre (as RAP), and lyrics to the contents list.
					else if (genre.equals("RAP"))
					{
						contents.add(new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.RAP, lyrics));
					}

					// Else if the genre is CLASSICAL, add a new song with the title, year, id, type, lyrics, length, artist, composer, genre (as CLASSICAL), and lyrics to the contents list.
					else if (genre.equals("CLASSICAL"))
					{
						contents.add(new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.CLASSICAL, lyrics));
					}
				}
				
				// Else if the content is an audiobook, execute the following code.
				else if (type.equals("AUDIOBOOK"))
				{
					id = scan.nextLine();       	// Initialize id to the next line of the store file.
					title = scan.nextLine();		// Initialize title to the next line of the store file.
					year = scan.nextInt();			// Initialize year to the next integer in the store file.
					scan.nextLine();				// Proceed to the next line after reading the integer.
					length = scan.nextInt();    	// Initialize length to the next integer in the store file.
					scan.nextLine();            	// Proceed to the next line after reading the integer.
					author = scan.nextLine();   	// Initialize author to the next line in the store file.
					narrator = scan.nextLine(); 	// Initialize narrator to the next line in the store file.
					numChapters = scan.nextInt();	// Initialize numChapters to the next integer in the store file.
					scan.nextLine();				// Proceed to the next line after readign the integer.

					// Create a for loop that loops as many times as there are chapters.
					for (int i = 0; i < numChapters; i++)
					{
						chapterTitles.add(scan.nextLine());
					}

					// Create a for loop that loops through the chapterTitles ArrayList.
					for (String element : chapterTitles)
					{
						// Create an empty string called chatperLine
						String line = ("");

						// Initialize chapterLines to the next integer in the store file.
						chapterLines = scan.nextInt();
						scan.nextLine();

						// Create a for loop that loops as many times as there are lines in the chapter.
						for (int i = 0; i < chapterLines; i++)
						{
							// Add the next line to the line variable and skip to the next line.
							line += scan.nextLine() + "\n";
						}
						// Add the line to the chapters ArrayList.
						chapters.add(line);
					}
					// Add an audiobook with all the necessary parameters.
					AudioBook audiobook = new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chapters);
					contents.add(audiobook);
				}
			}
			// Close the scanner.ß
			scan.close();

			// Return the contents list.
			return(contents);
		}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		private ArrayList<String> makeHPChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("The Riddle House");
			titles.add("The Scar");
			titles.add("The Invitation");
			titles.add("Back to The Burrow");
			return titles;
		}
		
		private ArrayList<String> makeHPChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("In which we learn of the mysterious murders\r\n"
					+ " in the Riddle House fifty years ago, \r\n"
					+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
					+ "and how the Riddle House fell into disrepair. ");
			chapters.add("In which Harry awakens from a bad dream, \r\n"
					+ "his scar burning, we recap Harry's previous adventures, \r\n"
					+ "and he writes a letter to his godfather.");
			chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
					+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
					+ " with her family and attend the World Quidditch Cup finals.");
			chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
					+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
					+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
					+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
					+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
			return chapters;
		}
		
		private ArrayList<String> makeMDChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Loomings.");
			titles.add("The Carpet-Bag.");
			titles.add("The Spouter-Inn.");
			return titles;
		}
		private ArrayList<String> makeMDChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
					+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
					+ " I thought I would sail about a little and see the watery part of the world.");
			chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
					+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
					+ "I duly arrived in New Bedford. It was a Saturday night in December.");
			chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
					+ "low, straggling entry with old-fashioned wainscots, \r\n"
					+ "reminding one of the bulwarks of some condemned old craft.");
			return chapters;
		}
		
		private ArrayList<String> makeSHChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Prologue");
			titles.add("Chapter 1");
			titles.add("Chapter 2");
			titles.add("Chapter 3");
			return titles;
		}
		
		private ArrayList<String> makeSHChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("The gale tore at him and he felt its bite deep within\r\n"
					+ "and he knew that if they did not make landfall in three days they would all be dead");
			chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
					+ "because he was ashore and the room unbelieveable");
			chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
					+ "where you come from, how ou got here, and what acts of piracy you have committed.");
			chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
			return chapters;
		}
		
		// Podcast Seasons
		/*
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/

		// Create a method that takes in a string parameter and searches the titles map for that title.
		// The method returns the integer value associated with that title.
		public int getTitleIndex(String title) 
		{
			// If the title is not in the titles map, throw a NullPointerException.
			return(titles.get(title));
		}

		// Create a method called getArtistIndices that takes in a string parameter and searches the artists
		// map for that artist. The method returns the ArrayList of integers representing the indices of the artist.
		public ArrayList<Integer> getArtistIndices(String artist) 
		{
			// if the artist is not in the artists map, throw a NullPointerException.
			return(artists.get(artist));
		}

		// Create a method called getGenreIndices that takes in a string parameter and searches the genres
		// map for that artist. The method returns the ArrayList of integers representing the indices of the songs with that genre.
		public ArrayList<Integer> getGenreIndices(String genre)
		{
			return(genres.get(genre));
		}
}
