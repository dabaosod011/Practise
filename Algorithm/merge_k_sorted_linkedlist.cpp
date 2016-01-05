#include <algorithm>
#include <cstdlib>
#include <iostream>
#include <list>
#include <queue>
#include <set>
#include <map>
#include <numeric>
#include <sstream>
#include <stack>
#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <time.h>
#include <unordered_map>
#include <unordered_set>
#include <vector>

using namespace std;

/**
**	Linked list
***/
struct ListNode 
{
	int val;
	ListNode *next;
	ListNode(int x) : val(x), next(NULL) {}
};

ListNode* createLinkedList(std::vector<int> &nodes)
{
	if (nodes.size() == 0)
		return NULL;

	ListNode *head = new ListNode(nodes[0]);
	ListNode *tmp = head;
	for (int i=1; i< nodes.size(); i++)
	{
		tmp->next = new ListNode(nodes[i]);
		tmp = tmp->next;
	}

	return head;
}

void dispayLinkedList(ListNode* head)
{
	for ( ListNode *idx = head; idx != NULL; idx = idx->next )
		std::cout << idx->val << " " ; 
	std::cout << std::endl;
}

void destoryLinkedlist(ListNode* head)
{
	ListNode *tmp;
	while (head != NULL)
	{
		tmp = head;
		head = head->next;
		tmp->next = NULL;
		delete tmp;
	}
}

ListNode *mergeKLists(vector<ListNode *> &lists) 
{
	if(lists.size() == 0)
		return NULL;

	if(lists.size() == 1)
		return lists[0];

	ListNode *head = lists[0];			
	for (int i=1; i<lists.size(); i++)
	{
		//	merge head and lists[i]
		ListNode *second = lists[i];
		ListNode *newhead = NULL;

		if(head == NULL)
		{
			newhead = second;
			continue;
		}
		if(second == NULL)
		{
			newhead = head;
			continue;
		}
		
		if(head->val < second->val)  
		{  
			newhead = head;
			head = head->next;  
		}  
		else  
		{  
			newhead = second;
			second = second->next;  
		}  	
		ListNode *newtail = newhead;  

		while(head!=NULL && second!=NULL)  
		{  
			if(head->val < second->val)  
			{  
				newtail->next = head;  
				head = head->next;  
			}  
			else  
			{  
				newtail->next = second;  
				second = second->next;  
			}  	
			newtail = newtail->next;  
		}

		if(head != NULL)   
			newtail->next = head;   

		if(second != NULL)  
			newtail->next = second;  

		head = newhead;
	}

	return head;			
}



int main()
{
	std::cout << "running merge_k_sorted_lists_test case2..." <<std::endl;

	vector<ListNode* > lists;
	for (int i=0; i < 50000; i++)
	{
		std::vector<int> nodes;
		nodes.push_back(std::rand());
		ListNode *head1 = createLinkedList(nodes);
		lists.push_back(head1);
	}

	ListNode *newhead = mergeKLists(lists);
	dispayLinkedList(newhead);
	destoryLinkedlist(newhead);
}