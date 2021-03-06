#include <ida.hpp>
#include <idp.hpp>
#include <loader.hpp>
#include <funcs.hpp>
#include <kernwin.hpp>
#include <bytes.hpp> 
#include <diskio.hpp>
#include <pro.h>

int IDAP_init(void)
{
	// Do checks here to ensure your plug-in is being used within
	// an environment it was written for. Return PLUGIN_SKIP if the 	
	// checks fail, otherwise return PLUGIN_KEEP.

	return PLUGIN_KEEP;
}

void IDAP_term(void)
{
	// Stuff to do when exiting, generally you'd put any sort
	// of clean-up jobs here.
	return;
}

// The plugin can be passed an integer argument from the plugins.cfg
// file. This can be useful when you want the one plug-in to do
// something different depending on the hot-key pressed or menu
// item selected.
void IDAP_run(int arg)
{
	// The "meat" of your plug-in
	
	msg("Hello world!\n");
	
	FILE *fp = ecreate("D:\\function.txt");
	char buf[2048];

	qsnprintf(buf, sizeof(buf)-1 , "Total function numbers: %d\n\n\n", get_func_qty());
	ewrite(fp, buf, strlen(buf));
	//msg("\n\n\n\n %d \n\n\n", get_func_qty());

	for (int f = 0; f < get_func_qty(); f++)
	{
		// getn_func() returns a func_t struct for the function number supplied
		func_t *curFunc = getn_func(f);
		char funcName[MAXSTR];

		// get_func_name gets the name of a function, stored in funcName
		get_func_name(curFunc->startEA,	funcName,	sizeof(funcName)-1);
		
		//if (curFunc->refqty == 0){	
		qsnprintf(buf, sizeof(buf)-1 , "%s:\t%d\n",  funcName, curFunc->refqty);
		ewrite(fp, buf, strlen(buf));
		
		//	msg("%s:\t%d\n", funcName, curFunc->refqty);
		//}
	}

	eclose(fp);

	msg("Done!\n");

	return;
}

// There isn't much use for these yet, but I set them anyway.
char IDAP_comment[] 	= "To find the unusedfunctions";
char IDAP_help[] 		= "Unused_functions";

// The name of the plug-in displayed in the Edit->Plugins menu. It can 
// be overridden in the user's plugins.cfg file.
char IDAP_name[] 		= "Unused_functions";

// The hot-key the user can use to run your plug-in.
char IDAP_hotkey[] 	= "Alt-X";

// The all-important exported PLUGIN object
plugin_t PLUGIN =
{
	IDP_INTERFACE_VERSION,	// IDA version plug-in is written for
	0,					// Flags (see below)
	IDAP_init,			// Initialisation function
	IDAP_term,			// Clean-up function
	IDAP_run,				// Main plug-in body
	IDAP_comment,			// Comment 
	IDAP_help,			// As above 
	IDAP_name,			// Plug-in name shown in 
	// Edit->Plugins menu
	IDAP_hotkey			// Hot key to run the plug-in
};
