//  MostFrequentWords.m


#import "MostFrequentWords.h"

@implementation TrieNode

- (void) initSetup {
    isEnd = 0;
    frequency = 0;
    for( int i = 0; i < MAX_CHARS; ++i )
        child[i] = NULL;
}

- (id) init {
    self = [super init];
    if (self != nil) {
        // initializations go here.
    }
    return self;
}

/*
-(id)copyWithZone:(NSZone *)zone
{
    // We'll ignore the zone for now
    TrieNode *another = [[TrieNode alloc] init];
    another->isEnd = self->isEnd;
    another->frequency = self->frequency;
    //another->child = [[NSMutableArray alloc] initWithArray:child copyItems:YES];
    return another;
}
*/
@end

@implementation Controller

- (id) init {
    self = [super init];
    if (self != nil) {
        // initializations go here.
    }
    return self;
}

- (void) insertIntoDict:(NSMutableDictionary*) dict :(TrieNode**) root :(const char*) word {
  //insert word into dict
  
    NSString* w = [NSString stringWithUTF8String:word];
    NSNumber *n = [dict objectForKey:w];
    if (n != nil) {
        //Word already present. Increment count
        NSNumber *nextValue = [NSNumber numberWithInt:[n intValue] + 1];
        [dict setObject:nextValue  forKey:w];
    } else {
        //case 1: Word not present. Place left. Add word and increment count
        //iCount: if iCount == n implies that dict is full
        if (iCount < dCapacity) {
            //(*root)->frequency;
            NSNumber *nextValue = [NSNumber numberWithInt: 1];
            [dict setObject:nextValue  forKey:w];
            iCount++;
        } else {
            //No place left:
            //calculate min freq in dict
            NSNumber *minValue  = [[dict allValues] valueForKeyPath:@"@min.intValue"];
            
            if ((*root)->frequency > [minValue intValue]) {
                for (NSString* key in dict) {
                    NSNumber *value = [dict objectForKey:key];
                    if ([minValue intValue] == [value intValue]) {
                        [dict removeObjectForKey:key];
                        break;
                    }
                }
                NSNumber *newValue = [NSNumber numberWithInt: (*root)->frequency];
                [dict setObject:newValue forKey:w];
            }
        }
    }
    
    
}

// Inserts a new word to both Trie and Heap
- (void) insertUtil:(TrieNode**) root :(NSMutableDictionary*) dict :(const char*) word :(const char*) dupWord{
    // Base Case
    if ( *root == NULL )
        *root = newTrieNode();
    
    //  There are still more characters in word
    if ( *word != '\0' )
        [self insertUtil:(&((*root)->child[ tolower( *word ) - 97 ])) :dict :(word + 1) :dupWord ];
    else // The complete word is processed
    {
        // word is already present, increase the frequency
        if ( (*root)->isEnd )
            ++( (*root)->frequency );
        else
        {
            (*root)->isEnd = 1;
            (*root)->frequency = 1;
        }
        
        // Insert in min heap also
        [self insertIntoDict :dict :root :dupWord];
    }
}


- (void) insertIntoTriePlusDict: (const char*) word :(TrieNode**) trie :(NSMutableDictionary*) dict {
    [self insertUtil:trie :dict :word :word];
    
}

- (NSArray*) findNMostFrequent: (NSString *)text :(int) n {
    
    //char * text =
    //NSLog(@"Entering findNFreq");
    //n = 3;
    //NSString *s = @"Hello how are you this is hello how is this complex this wow this how";
    //const char *c = [s UTF8String];
    //text = c;
    iCount = 0;
    //minValue = 0;
    dCapacity = n;
    NSMutableDictionary* dict = [[NSMutableDictionary alloc]initWithCapacity:n];
    // Create an empty Trie
    TrieNode* root = NULL;
    
    // A buffer to store one word at a time
    //char word[MAX_WORD_SIZE];
    NSArray *myWords = [text componentsSeparatedByString:@" "];
    // Check if EOF works correctly
    //while( scanf( text, "%s", word) != EOF ) {
    for (int i = 0; i < [myWords count]; i++) {
        //See if this gets out
        NSLog(@"Entering Word while");
        const char *w = [myWords[i] UTF8String];
        [self insertIntoTriePlusDict:w:&root:dict];
    }
    NSLog(@"Done with the while loop");
    NSArray *sorted = [dict keysSortedByValueUsingSelector:@selector(compare:)];
    for (int i =0; i < [sorted count]; i++) {
        NSLog(@"String: %@", sorted[i]);
    }
    return sorted;
}


TrieNode* newTrieNode()
{
    // Allocate memory for Trie Node
    TrieNode* trieNode = [[TrieNode alloc] init];
    [trieNode initSetup];
    
    return trieNode;
}

@end









