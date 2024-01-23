import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.lang.model.util.ElementScanner6;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	// Method download throws exception
	public void download(AudioContent content) throws AudioContentDownloadedException  
	{
		// If the audio content is a song, execute the following code.
		if (content.getType().equals(Song.TYPENAME))
		{
			// Type cast content to a song object.
			Song song = (Song)content;
			
			// Create an enhanced for loop to loop through the  songs list.
			for (Song element : songs)
			{
				// If the element is in songs list, throw an AudioContentDownloadedException.
				if(element.equals(song))
				{
					throw new AudioContentDownloadedException("Song " + element.getTitle() + " already downloaded");
				}
			}
			// if the element is not in the songs list.
			songs.add(song);
		}
		// Else if the content is an audio book, execute the following code.
		else if (content.getType().equals(AudioBook.TYPENAME))
		{
			// Type cast content to an audio book object.
			AudioBook book = (AudioBook)content;

			// Create an enhanced for loop to loop through the songs list.
			for (AudioBook element : audiobooks)
			{
				// If the element is in the audiobooks list, throw an AudioContentDownloaded Exception.
				if(element.equals(book)) 
				{
					throw new AudioContentDownloadedException("AudioBook " + element.getTitle() + " already downloaded");
				}
			}
			// If the element is not in the audiobooks list, add it.
			audiobooks.add(book);
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		// Create a for loop that loops through the audiobooks arrayList.
		for (int i = 0; i < audiobooks.size(); i++)
		{
			// Print the number of the audiobook followed by ". "
			System.out.print((i+1) + ". ");

			// Print the info of the audiobook at index i.
			audiobooks.get(i).printInfo();

			// Print an empty line.
			System.out.println();
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		// Create a for loop that loops through the podcasts arrayList.
		for (int i = 0; i < playlists.size(); i++)
		{
			// Print the number of the playlist followed by ". "
			System.out.print((i+1) + ". ");

			// Print the title of the playlist at index i.
			System.out.println(playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<String>();

		// Create an enhanced for loop to loop through the elements of the songs list.
		for (Song element : songs)
		{
			// If the artists list does not contain the artist of the current element in the songs list, add the artist to the artists list.
			if(!artists.contains(element.getArtist()))
			{
				artists.add(element.getArtist());
			}
		}
		// Create a for loop that loops as many times as the size of the artists list.
		for (int i = 0; i < artists.size(); i++)
		{
			// Print the number of the artist.
			System.out.print((i+1) + ". ");

			// Print the artist in the artists list at index i.
			System.out.println(artists.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	// The method throws an exception.
	public void deleteSong(int index) throws AudioContentNotFoundException
	{
		// If index is out of bounds, throw an AudioContentNotFoundException.
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song Not Found");
		}
		// Create a for loop to loop through the playlists list.
		for (int i = 0; i < playlists.size(); i++)
		{
			// Create a for loop to loop through the contents of each element of the playlists list.
			for (int x = 0; x < playlists.get(i).getContent().size(); x++)
			{
				// If the current element is equal to the song at index (index - 1), remove it from that playlist.
				if ((playlists.get(i).getContent().get(x)).equals(songs.get(index-1)))
				{
					playlists.get(i).getContent().remove(x);
				}
			}
		}
		// Remove the song from the songs list.
		songs.remove(index-1);
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		// Compares first and second song by year.
		public int compare(Song firstSong, Song secondSong)
		{
			// If the first song is newer, return 1.
			if (firstSong.getYear() > secondSong.getYear())
			{
				return(1);
			}
			// If the second song is newer, return -1.
			else if (firstSong.getYear() < secondSong.getYear())
			{
				return(-1);
			}
			// Else, return 0.
			else
			{
				return(0);
			}
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		// Compares first and second song by length.
		public int compare (Song firstSong, Song secondSong)
		{
			// If the first song is longer, return 1.
			if (firstSong.getLength() > secondSong.getLength())
			{
				return(1);
			}
			// If the second song is longer, return -1.
			else if (firstSong.getLength() < secondSong.getLength())
			{
				return(-1);
			}
			// Else, return 0.
			else
			{
				return(0);
			}
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list. Throws exception.
	public void playSong(int index) throws AudioContentNotFoundException
	{
		// If the index is oubt of bounds, throw an AudioContentNotFoundException.
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song Not Found");
		}
		// Play the element of the songs list at index (index-1).
		songs.get(index-1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks. Throws exceptions.
	public void playAudioBook(int index, int chapter) throws AudioContentNotFoundException, ChapterNotFoundException
	{
		// If the index is out of bounds, throw an AudioContentNotFoundException.
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
		// If the chapter is out of bounds, throw a ChapterNotFoundException.
		if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters())
		{
			throw new ChapterNotFoundException("Chapter Not Found");
		}
		// Select the chapter of the audiobook
		audiobooks.get(index-1).selectChapter(chapter);
		
		// Play the audiobook.
		audiobooks.get(index-1).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook. Throws exception.
	public void printAudioBookTOC(int index) throws AudioContentNotFoundException
	{
		// if the index is out of bounds, throw an AudioContentNotFoundException.
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
		// Print the table of contents of the audiobok at index (index - 1).
		audiobooks.get(index-1).printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist. Throws an exception.
	public void makePlaylist(String title) throws PlaylistAlreadyExistsException
	{
		// Create a for loop that loops through the elements of the playlists list.
		for (Playlist element : playlists)
		{
			// If there exists a playlist with the same title, throw a PlaylistAlreadyExistsException.
			if (element.getTitle().equals(title))
			{
				throw new PlaylistAlreadyExistsException("Playlist " + title + " Already Exists");
			}
		}
		// Create a new playlist object using the title parameter.
		Playlist newPlaylist = new Playlist(title);

		// Add the new playlist object to the playlists list.
		playlists.add(newPlaylist);
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists. Throws an exception.
	public void printPlaylist(String title) throws PlaylistNotFoundException
	{
		// Create a for loop that loops through the elements of the playlists list.
		for (Playlist element : playlists)
		{
			// If there is a playlist with the correct title in the playlists list, print the contents.
			if (element.getTitle().equals(title))
			{
				element.printContents();
				return;
			}
		}
		// If the title is not the name of one of the playlists, throw a PlaylistNotFoundException.
		throw new PlaylistNotFoundException("Playlist Not Found");
	}
	
	// Play all content in a playlist. Throws an exception.
	public void playPlaylist(String playlistTitle) throws PlaylistNotFoundException
	{
		// Create a for loop that loops through the playlists array.
		for (Playlist element : playlists)
		{
			// If the current element of the list has the same title as playlistTitle, play that playlist.
			if (element.getTitle().equals(playlistTitle))
			{
				element.playAll();
				return;
			}
		}
		// If the playlist is not in the list, throw a PlaylistNotFoundException.
		throw new PlaylistNotFoundException("Playlist Not Found");
	}
	
	// Play a specific song/audiobook in a playlist. Throws exceptions.
	public void playPlaylist(String playlistTitle, int indexInPL) throws AudioContentNotFoundException, PlaylistNotFoundException
	{
		// Create a for loop that loops through the playlists list.
		for (Playlist element : playlists)
		{	
			// If the title parameter matches the title of a playlist, execute the following code.
			if (element.getTitle().equals(playlistTitle))
			{
				// Check the validity of the index.
				if (indexInPL < 1 || indexInPL > element.getContent().size())
				{
					// If the index is invalid, throw an AudioContentNotFoundException.
					throw new AudioContentNotFoundException("Audio Content Not Found");
				}
				// Otherwise, play the indexInPL.
				element.play(indexInPL);
				return;
			}
		}
		// Throw a PlaylistNotFoundException.
		throw new PlaylistNotFoundException("Playlist not found");
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list. Throws exceptions.
	public void addContentToPlaylist(String type, int index, String playlistTitle) throws AudioContentNotFoundException, InvalidTypeException, PlaylistNotFoundException
	{
		// Create an enhanced for loop to loop through the playlists list.
		for (Playlist playlist : playlists) 
		{
			// If the element's title is equal to the parameter playlistTitle, execute the following code.
			if (playlist.getTitle().equals(playlistTitle))
			{
				// If the the parameter type (not case sensitive) is equal to the TYPENAME of Song, execute the following code.
				if (Song.TYPENAME.equalsIgnoreCase(type))
				{
					// If the index is out of bounds, throw an AudioContentNotFoundException.
					if (index < 1 || index > songs.size())
					{
						throw new AudioContentNotFoundException("Song Not Found");
					}
					// Add the song at index (index - ) to the playlist element.
					playlist.addContent(songs.get(index-1));
					return;
				}
				// Else if the parameter type (not case sensitive) is equal to the TYPENAME of AudioBook, execute the following code.
				else if (AudioBook.TYPENAME.equalsIgnoreCase(type))
				{
					// If the index is out of bounds, throw an AudioContentNotFoundException.
					if (index < 1 || index > audiobooks.size())
					{
						throw new AudioContentNotFoundException("AudioBook Not Found");
					}
					// Add the audobook at index (index - 1) to the playlist element.
					playlist.addContent(audiobooks.get(index-1));
					return;
				}
				// Else, throw an InvalidTypeException.
				else
				{
					throw new InvalidTypeException("Invalid Type");
				}
			}
		}
		// Throw a PlaylistNotFound exception.
		throw new PlaylistNotFoundException("Playlist Not Found");
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid. Throws exceptions.
	public void delContentFromPlaylist(int index, String title) throws AudioContentNotFoundException, PlaylistNotFoundException
	{
		// Create a for loop that loops through the playlists list.
		for (int i = 0; i < playlists.size(); i++)
		{
			// If the current playlist's title is equal to the parameter title, execute the following code.
			if (playlists.get(i).getTitle().equals(title))
			{
				// If the index is out of bounds, throw an AudioContentNotFoundException.
				if (index < 1 || index > playlists.get(i).getContent().size())
				{
					throw new AudioContentNotFoundException("Audio Content Not Found");
				}
				// Delete the content at index index.
				playlists.get(i).deleteContent(index);
			}
		}
		// Throw a PlaylistNotFoundException.
		throw new PlaylistNotFoundException("Playlist Not Found");
	}
}

// Create a class called AudioContentDownloadedException.
class AudioContentDownloadedException extends RuntimeException
{
	public AudioContentDownloadedException() {}
	public AudioContentDownloadedException(String message)
	{
		super(message);
	}
}

// Create a class called AudioContentNotFoundException.
class AudioContentNotFoundException extends RuntimeException
{
	public AudioContentNotFoundException() {}
	public AudioContentNotFoundException(String message)
	{
		super(message);
	}
}

// Create a class called ChapterNotFoundException.
class ChapterNotFoundException extends RuntimeException
{
	public ChapterNotFoundException() {}
	public ChapterNotFoundException(String message)
	{
		super(message);
	}
}

// Create a class called PlaylistAlreadyExistsException.
class PlaylistAlreadyExistsException extends RuntimeException
{
	public PlaylistAlreadyExistsException() {}
	public PlaylistAlreadyExistsException(String message)
	{
		super(message);
	}
}

// Create a class called PlaylistNotFoundException.
class PlaylistNotFoundException extends RuntimeException
{
	public PlaylistNotFoundException() {}
	public PlaylistNotFoundException(String message)
	{
		super(message);
	}
}

// Create a class called InvalidTypeException.
class InvalidTypeException extends RuntimeException
{
	public InvalidTypeException() {}
	public InvalidTypeException(String message)
	{
		super(message);
	}
}