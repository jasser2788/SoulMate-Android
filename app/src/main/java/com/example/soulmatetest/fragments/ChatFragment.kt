package com.example.soulmatetest.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.soulmatetest.R
import com.example.soulmatetest.databinding.FragmentChatBinding
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory


class ChatFragment : Fragment() {
    private val client = ChatClient.instance()

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        val selectedUser = bundle!!.getString("selectedUser").toString()

        val channelId = bundle!!.getString("channelId").toString()

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        if(selectedUser != "null") {
            client.createChannel(
                channelType = "messaging",
                members = listOf(client.getCurrentUser()!!.id, selectedUser)
            ).enqueue { result ->
                if (result.isSuccess) {
                    setupMessages(result.data().cid)
                } else {
                    Log.e("UsersAdapter", result.error().message.toString())
                }
            }
        }
        else setupMessages(channelId)

        binding.messagesHeaderView.setBackButtonClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun setupMessages(string: String) {

        val factory = MessageListViewModelFactory(cid = string)

        val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
        val messageListViewModel: MessageListViewModel by viewModels { factory }
        val messageInputViewModel: MessageInputViewModel by viewModels { factory }

        messageListHeaderViewModel.bindView(binding.messagesHeaderView, viewLifecycleOwner)
        messageListViewModel.bindView(binding.messageList, viewLifecycleOwner)
        messageInputViewModel.bindView(binding.messageInputView, viewLifecycleOwner)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}