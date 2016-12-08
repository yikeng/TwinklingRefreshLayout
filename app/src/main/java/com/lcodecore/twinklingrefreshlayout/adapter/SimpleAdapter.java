package com.lcodecore.twinklingrefreshlayout.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.twinklingrefreshlayout.R;
import com.lcodecore.twinklingrefreshlayout.beans.Card;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends BaseAdapter {

        private List<Card> cards = new ArrayList<>();

        @Override
        public int getCount() {
            return cards.size();
        }

        @Override
        public Card getItem(int position) {
            return cards.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_title.setText(cards.get(position).title);
            holder.tv_subTitle.setText(cards.get(position).info);
            holder.mImageView.setImageResource(cards.get(position).imageSrc);

            return convertView;
        }

        class ViewHolder {
            final ImageView mImageView;
            final TextView tv_title;
            final TextView tv_subTitle;

            ViewHolder(View view) {
                mImageView = (ImageView) view.findViewById(R.id.iv_cover);
                tv_title = (TextView) view.findViewById(R.id.tv_title);
                tv_subTitle = (TextView) view.findViewById(R.id.tv_subtitle);
            }
        }

        public void refreshCard(){
            cards.clear();
            cards.add(new Card("God of Light", "点亮世界之光",R.drawable.card_cover1));
            cards.add(new Card("我的手机与众不同", "专题",R.drawable.card_cover2));
            cards.add(new Card("BlackLight", "做最纯粹的微博客户端",R.drawable.card_cover3));
            cards.add(new Card("BuzzFeed", "最好玩的新闻在这里",R.drawable.card_cover4));
            cards.add(new Card("Nester", "专治各种熊孩子",R.drawable.card_cover5));
            notifyDataSetChanged();
        }

        public void loadMoreCard(){
            cards.add(new Card("二次元专题", "啊喂，别总想去四维空间啦",R.drawable.card_cover6));
            cards.add(new Card("Music Player", "闻其名，余音绕梁",R.drawable.card_cover7));
            cards.add(new Card("el", "剪纸人の唯美旅程",R.drawable.card_cover8));
            cards.add(new Card("God of Light", "点亮世界之光",R.drawable.card_cover1));
            cards.add(new Card("BlackLight", "做最纯粹的微博客户端",R.drawable.card_cover3));
            notifyDataSetChanged();
        }
    }