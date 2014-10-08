//
//  MostFrequentWords.h

#import <string.h>
#import <ctype.h>

# define MAX_CHARS 26

// A Trie node
@interface TrieNode: NSObject {
@public
    bool isEnd; // indicates end of word
    unsigned frequency;  // the number of occurrences of a word
    TrieNode* child[MAX_CHARS]; // represents 26 slots each for 'a' to 'z'.
};
@end


@interface Controller: NSObject {
    int iCount;
    int dCapacity;
    //int minValue;
}
@end;

@interface TextDelegate : UIViewController <UITextFieldDelegate>
{
	NSString *enteredTag; 
}

@end




@interface YellowViewController : UIViewController <UIActionSheetDelegate> {
	
	UIViewController *rootController; 
	NSString *tagString; 
	XMLParser *parser;
	UIImage *backGround; 
	
	UIImageView *bgView; 
	UIImageView *imgView; 
	UIActivityIndicatorView *activityView;
	
	int attributeFun; 
	int iCount; 

	int inputMode;
	int inValidateTimer; 
	NSTimer *anmiTimer;
	
	TextDelegate *tDele; 
	IBOutlet UITextField *textView;
	
	NSString *serverMessage; 
	NSString *serverMessageTitle; 
	
	UILabel *ownerNameLabel; 
	//UILabel *ownerNames; 
	
	int deviceWidth;
	int deviceHeight; 
	float timeInterval; 
	
	
	int labelYCord;
	int labelHeight; 
	int labelWidth; 
	int labelCollWidth; 
	
	NSMutableArray *imageArray; 
	NSMutableArray *ownerArray; 
	NSMutableArray *indexArray;
	
	BOOL isIPad; 
	BOOL isIPhoneHd; 
	
	int cArray[10]; 
	int cArrayLastPointer;
	
	
	//NSMutableData *asyncImageData; 
	//NSURLConnection *theConnection;
	int asyncArrayIndex; 
	
	UrlFerret *receiver; 
	
	//UIView *containerView; 
	UIImageView *view2; 
	
	UILabel *waitingLabel; 
	UILabel *waitingLabel1; 
	UILabel *floatingLabel; 
 	UIImageView* flakeViewX;
	
	UIImageView* brandView; 
	
	BOOL rated; 
		

}
@property (retain) UIViewController *rootController; 
@property (retain) TextDelegate *tDele; 
@property (retain) UITextField *textView; 
 
@property (retain)	NSMutableArray *imageArray; 
@property (retain)	NSMutableArray *ownerArray; 
@property (retain)	NSMutableArray *indexArray; 

- (IBAction)textFieldDoneEditing:(id)sender;
- (IBAction)yellowButtonPressed:(id)sender;

//- (void) doit: (NSString *) tag;

- (void) doit; 
@end


