package uz.ilkhomkhuja.daggerhiltexample.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import uz.ilkhomkhuja.daggerhiltexample.R
import uz.ilkhomkhuja.daggerhiltexample.database.entity.UserEntity
import uz.ilkhomkhuja.daggerhiltexample.databinding.ItemUserBinding

class UserAdapter : ListAdapter<UserEntity, UserAdapter.Vh>(UserDiffUtil()) {

    inner class Vh(private var itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(userEntity: UserEntity) {
            itemUserBinding.apply {
                loginTv.text = userEntity.login
                val request = ImageRequest.Builder(image.context)
                    .data(userEntity.avatarUrl)
                    .target(
                        onStart = {
                            image.setImageDrawable(
                                ContextCompat.getDrawable(
                                    image.context,
                                    R.drawable.ic_launcher_foreground
                                )
                            )
                        },
                        onSuccess = { result ->
                            image.setImageDrawable(result)
                        },
                        onError = {
                            image.setImageDrawable(
                                ContextCompat.getDrawable(
                                    image.context,
                                    android.R.drawable.stat_notify_error
                                )
                            )
                        }
                    ).build()
                image.context.imageLoader.enqueue(request)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }

    class UserDiffUtil : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }
    }
}