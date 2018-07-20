package indi.aben20807.rebridgechat.connect;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import indi.aben20807.rebridgechat.bridge.Card;

public class Message implements Serializable {

  private static final long serialVersionUID = 6719918041213189867L;
  private String text;
  private Card card;
  private MessageOption option;
  private final ReadWriteLock lock = new ReentrantReadWriteLock();

  public Message(Object object, MessageOption option) {
    lock.writeLock().lock();
    setOption(option);
    switch (option) {
      case CHAT:
      case CALL:
      case COMMAND:
        setText((String) object);
        break;
      case CARD:
        setCard((Card) object);
        break;
      default:
        break;
    }
    lock.writeLock().unlock();
  }

  public Message(String text) {
    lock.writeLock().lock();
    setOption(MessageOption.COMMAND);
    setText(text);
    lock.writeLock().unlock();
  }

  public Object getContent() {
    try {
      lock.readLock().lock();
      switch (option) {
        case CHAT:
        case CALL:
        case COMMAND:
          return getText();
        case CARD:
          return getCard();
        default:
          return null;
      }
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public String toString() {
    switch (option) {
      case CHAT:
      case CALL:
      case COMMAND:
        return getText();
      case CARD:
        return getCard().toString();
      default:
        return "Message out of options";
    }
  }

  private String getText() {
    return text;
  }

  private void setText(String text) {
    this.text = text;
  }

  public MessageOption getOption() {
    return option;
  }

  private void setOption(MessageOption option) {
    this.option = option;
  }

  private Card getCard() {
    return card;
  }

  private void setCard(Card card) {
    this.card = card;
  }
}
